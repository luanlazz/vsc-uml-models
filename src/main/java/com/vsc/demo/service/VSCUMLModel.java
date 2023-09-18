package com.vsc.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.vsc.demo.dao.AttributeEntity;
import com.vsc.demo.dao.ClassEntity;
import com.vsc.demo.dao.DiagramEntity;
import com.vsc.demo.dao.HistoryChangeEntity;
import com.vsc.demo.dao.HistoryChangeType;
import com.vsc.demo.dao.OperationEntity;
import com.vsc.demo.dao.OperationParameterEntity;
import com.vsc.demo.dao.UMLElementEntity;
import com.vsc.demo.dao.VersionEntity;
import com.vsc.demo.repository.AttributeRepository;
import com.vsc.demo.repository.ClassRepository;
import com.vsc.demo.repository.DiagramRepository;
import com.vsc.demo.repository.HistoryChangeRepository;
import com.vsc.demo.repository.OperationParameterRepository;
import com.vsc.demo.repository.OperationRepository;
import com.vsc.demo.repository.VersionRepository;
import com.vsc.demo.uml.models._class.ClassAttribute;
import com.vsc.demo.uml.models._class.ClassOperation;
import com.vsc.demo.uml.models._class.ClassStructure;
import com.vsc.demo.uml.models._class.OperationParameter;
import com.vsc.demo.uml.models._class.UMLElement;
import com.vsc.demo.uml.models._class.UMLModel;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@Component("VSCUMLModel")
public class VSCUMLModel {

	@Autowired
	private ClassRepository classRepository;
	@Autowired
	private DiagramRepository diagramRepository;
	@Autowired
	private AttributeRepository attributeRepository;
	@Autowired
	private OperationRepository operationRepository;
	@Autowired
	private OperationParameterRepository parameterRepository;
	@Autowired
	private VersionRepository versionRepository;
	@Autowired
	private HistoryChangeRepository historyChangeRepository;

	ArrayList<HistoryChangeEntity> historyChanges = new ArrayList<HistoryChangeEntity>();

	private VersionEntity version = null;

	public List<HistoryChangeEntity> versionControlUml(UMLModel newModel, boolean isNewModel) {
		try {
			this.historyChanges = new ArrayList<HistoryChangeEntity>();		
			
			this.version = new VersionEntity();
			this.versionRepository.save(this.version);
			
			if (isNewModel) {
				DiagramEntity model = this.saveNewModel(newModel);
				this.diagramRepository.save(model);
				System.out.println("Init new model: " + model.getName());
			} else {
				DiagramEntity model = this.loadModelById(newModel.getId());
				this.saveChanges(model, newModel);
			}

			if (this.historyChanges.isEmpty()) {
				this.versionRepository.delete(version);
			} else {
				this.historyChangeRepository.saveAll(this.historyChanges);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return this.historyChanges;
	}

	public DiagramEntity saveNewModel(UMLModel model) {
		DiagramEntity diagram = new DiagramEntity(model.getId(), model.getName(), model.getType(), this.version);
		this.diagramRepository.save(diagram);
		addChangeToHistory(diagram, diagram.getId(), diagram, HistoryChangeType.ADD, null, diagram.getName());

		for (ClassStructure _class : model.getClasses()) {
			ClassEntity _classEntity = classStructureToEntity(_class, diagram);
			classRepository.save(_classEntity);
			diagram.addClass(_classEntity);
			addChangeToHistory(diagram, _classEntity.getId(), _classEntity, HistoryChangeType.ADD, null,
					_classEntity.getName());

			for (AttributeEntity attribute : _classEntity.getAttributes()) {
				this.attributeRepository.save(attribute);
				addChangeToHistory(diagram, attribute.getId(), attribute, HistoryChangeType.ADD, null,
						attribute.getName());
			}

			for (OperationEntity operation : _classEntity.getOperations()) {
				this.operationRepository.save(operation);
				addChangeToHistory(diagram, operation.getId(), operation, HistoryChangeType.ADD, null,
						operation.getName());

				for (OperationParameterEntity parameter : operation.getParameters()) {
					this.parameterRepository.save(parameter);
					addChangeToHistory(diagram, parameter.getId(), parameter, HistoryChangeType.ADD, null,
							parameter.getName());
				}
			}
		}

		return diagram;
	}

	public void addChangeToHistory(DiagramEntity diagram, Long entityId, UMLElementEntity umlElement,
			HistoryChangeType changeType, String property, String value) {
		HistoryChangeEntity newClass = new HistoryChangeEntity(diagram, umlElement.getClass().getSimpleName(), entityId,
				changeType, property, value, this.version);
		this.historyChanges.add(newClass);
	}

	public DiagramEntity loadModelById(String id) {
		DiagramEntity diagramEntity = diagramRepository.findByUmlId(id);
		return diagramEntity;
	}

	public void saveChanges(DiagramEntity model, UMLModel newModel) {
		this.addedElements(model, newModel);
		this.removedElements(model, newModel);
		this.changedElements(model, newModel);
	}

	public void addedElements(DiagramEntity model, UMLModel newModel) {
		List<ClassEntity> addClasses = new ArrayList<ClassEntity>();
		List<AttributeEntity> addAttributes = new ArrayList<AttributeEntity>();
		List<OperationEntity> addOperations = new ArrayList<OperationEntity>();
		List<OperationParameterEntity> addParameters = new ArrayList<OperationParameterEntity>();

		for (ClassStructure _class : newModel.getClasses()) {
			ClassEntity classExists = findClassById(model.getClasses(), _class.getId());
			ClassEntity newClass = null;
			if (classExists == null) {
				newClass = classStructureToEntity(_class, model);
				addClasses.add(newClass);
//				continue;
			}

			for (ClassAttribute attribute : _class.getAttributes()) {
				AttributeEntity attributeExists = findAttributeById(classExists, attribute.getId());
				if (attributeExists == null) {
					addAttributes.add(classAttributeToEntity(attribute, classExists != null ? classExists : newClass));
				}
			}

			for (ClassOperation operation : _class.getOperations()) {
				OperationEntity operationExists = findOperationById(classExists, operation.getId());
				OperationEntity newOperation = null;
				if (operationExists == null) {
					newOperation = classOperationToEntity(operation, classExists != null ? classExists : newClass);
					addOperations.add(newOperation);
//					continue;
				}

				for (OperationParameter parameter : operation.getParameters()) {
					OperationParameterEntity parameterExists = findParameterById(operationExists, parameter.getId());
					if (parameterExists == null) {
						addParameters.add(opParameterToEntity(parameter,
								operationExists != null ? operationExists : newOperation));
					}
				}
			}
		}

		for (ClassEntity _class : addClasses) {
			classRepository.save(_class);
			addChangeToHistory(model, _class.getId(), _class, HistoryChangeType.ADD, null, _class.getName());
			System.out.println("new class: " + _class.getName());
		}

		for (AttributeEntity attribute : addAttributes) {
			attributeRepository.save(attribute);
			addChangeToHistory(model, attribute.getId(), attribute, HistoryChangeType.ADD, null, attribute.getName());
			System.out.println("new attribute: " + attribute.getName());
		}

		for (OperationEntity operation : addOperations) {
			operationRepository.save(operation);
			addChangeToHistory(model, operation.getId(), operation, HistoryChangeType.ADD, null, operation.getName());
			System.out.println("new operation: " + operation.getName());
		}

		for (OperationParameterEntity parameter : addParameters) {
			parameterRepository.save(parameter);
			addChangeToHistory(model, parameter.getId(), parameter, HistoryChangeType.ADD, null, parameter.getName());
			System.out.println("new parameter: " + parameter.getName());
		}
	}

	public void removedElements(DiagramEntity model, UMLModel newModel) {
		List<ClassEntity> removedClasses = new ArrayList<ClassEntity>();
		List<AttributeEntity> removedAttributes = new ArrayList<AttributeEntity>();
		List<OperationEntity> removedOperations = new ArrayList<OperationEntity>();
		List<OperationParameterEntity> removedParameters = new ArrayList<OperationParameterEntity>();

		ArrayList<UMLElement> classes = new ArrayList<UMLElement>(newModel.getClasses());
		for (ClassEntity _class : model.getClasses()) {
			ClassStructure classExists = (ClassStructure) findById(classes, _class.getIdUml());
			if (classExists == null) {
				removedClasses.add(_class);
				continue;
			}

			ArrayList<UMLElement> attributes = new ArrayList<UMLElement>(classExists.getAttributes());
			for (AttributeEntity attribute : _class.getAttributes()) {
				ClassAttribute attributeExists = (ClassAttribute) findById(attributes, attribute.getIdUml());
				if (attributeExists == null) {
					removedAttributes.add(attribute);
				}
			}

			ArrayList<UMLElement> operations = new ArrayList<UMLElement>(classExists.getOperations());
			for (OperationEntity operation : _class.getOperations()) {
				ClassOperation operationExists = (ClassOperation) findById(operations, operation.getIdUml());
				if (operationExists == null) {
					removedOperations.add(operation);
					continue;
				}

				ArrayList<UMLElement> parameters = new ArrayList<UMLElement>(operationExists.getParameters());
				for (OperationParameterEntity parameter : operation.getParameters()) {
					OperationParameter parameterExists = (OperationParameter) findById(parameters,
							parameter.getIdUml());
					if (parameterExists == null) {
						removedParameters.add(parameter);
					}
				}
			}
		}

		for (ClassEntity _class : removedClasses) {
			_class.setVersion(this.version);
			classRepository.softDelete(_class.getId());
			addChangeToHistory(model, _class.getId(), _class, HistoryChangeType.REMOVE, null, _class.getName());
			System.out.println("removed class: " + _class.getName());
		}

		for (AttributeEntity attribute : removedAttributes) {
			attribute.setVersion(this.version);
			attributeRepository.softDelete(attribute.getId());
			addChangeToHistory(model, attribute.getId(), attribute, HistoryChangeType.REMOVE, null,
					attribute.getName());
			System.out.println("removed attribute: " + attribute.getName());
		}

		for (OperationEntity operation : removedOperations) {
			operation.setVersion(this.version);
			operationRepository.softDelete(operation.getId());
			addChangeToHistory(model, operation.getId(), operation, HistoryChangeType.REMOVE, null,
					operation.getName());
			System.out.println("removed operation: " + operation.getName());
		}

		for (OperationParameterEntity parameter : removedParameters) {
			parameter.setVersion(this.version);
			parameterRepository.softDelete(parameter.getId());
			addChangeToHistory(model, parameter.getId(), parameter, HistoryChangeType.REMOVE, null,
					parameter.getName());
			System.out.println("removed parameter: " + parameter.getName());
		}
	}

	public void changedElements(DiagramEntity model, UMLModel newModel) {
		List<ClassEntity> changedClasses = new ArrayList<ClassEntity>();
		List<AttributeEntity> changedAttributes = new ArrayList<AttributeEntity>();
		List<OperationEntity> changedOperations = new ArrayList<OperationEntity>();
		List<OperationParameterEntity> changedParameters = new ArrayList<OperationParameterEntity>();

		for (ClassStructure _class : newModel.getClasses()) {
			ClassEntity classExists = findClassById(model.getClasses(), _class.getId());
			if (classExists != null) {
				boolean hasChanges = false;

				if (!classExists.getName().equals(_class.getName())) {
					// change name
					System.out.println("Class: " + classExists.getName() + " change name to: " + _class.getName());
					hasChanges = true;
					classExists.setName(_class.getName());
				}
//				if (!classExists().equals(_class.getSuperClasses())) {
//					// change name
//				}

				if (hasChanges) {
					changedClasses.add(classExists);
				}
			}

			for (ClassAttribute attribute : _class.getAttributes()) {
				AttributeEntity attributeExists = findAttributeById(classExists, attribute.getId());
				if (attributeExists != null) {
					boolean hasChanges = false;

					if (!attributeExists.getName().equals(attribute.getName())) {
						// change name
						System.out.println(
								"Attribute: " + attributeExists.getName() + " change name to: " + attribute.getName());
						hasChanges = true;
						attributeExists.setName(attribute.getName());
						addChangeToHistory(model, attributeExists.getId(), attributeExists, HistoryChangeType.CHANGE,
								"name", attributeExists.getName());
					}
//					if (!attributeExists.getIdType().toString().equals(attribute.getType())) {
//						// change type
//						System.out.println("Attribute: " + attributeExists.getIdType().toString() + " change type to: "
//								+ attribute.getType());
//					}
					if (!attributeExists.getVisibility().equals(attribute.getVisibility())) {
						// change visibility
						System.out.println("Attribute: " + attributeExists.getVisibility() + " change visibility to: "
								+ attribute.getVisibility());
						hasChanges = true;
						attributeExists.setVisibility(attribute.getVisibility());
						addChangeToHistory(model, attributeExists.getId(), attributeExists, HistoryChangeType.CHANGE,
								"visibility", attributeExists.getVisibility());
					}

					if (hasChanges) {
						changedAttributes.add(attributeExists);
					}
				}
			}

			for (ClassOperation operation : _class.getOperations()) {
				OperationEntity operationExists = findOperationById(classExists, operation.getId());
				if (operationExists != null) {
					boolean hasChanges = false;

					if (!operationExists.getName().equals(operation.getName())) {
						// change name
						System.out.println(
								"Operation: " + operationExists.getName() + " change name to: " + operation.getName());
						hasChanges = true;
						operationExists.setName(operation.getName());
						addChangeToHistory(model, operationExists.getId(), operationExists, HistoryChangeType.CHANGE,
								"name", operationExists.getName());
					}
//					if (!operationExists.getIdType().toString().equals(operation.getType())) {
//						// change type
//						System.out.println("Operation: " + operationExists.getIdType().toString() + " change type to: "
//								+ operation.getType());
//					}
					if (!operationExists.getVisibility().equals(operation.getVisibility())) {
						// change visibility
						System.out.println("Operation: " + operationExists.getVisibility() + " change visibility to: "
								+ operation.getVisibility());
						hasChanges = true;
						operationExists.setVisibility(operation.getVisibility());
						addChangeToHistory(model, operationExists.getId(), operationExists, HistoryChangeType.CHANGE,
								"visibility", operationExists.getVisibility());
					}

					if (hasChanges) {
						changedOperations.add(operationExists);
					}
				}

				for (OperationParameter parameter : operation.getParameters()) {
					OperationParameterEntity parameterExists = findParameterById(operationExists, parameter.getId());
					if (parameterExists != null) {
						boolean hasChanges = false;

						if (!parameterExists.getName().equals(parameter.getName())) {
							// change name
							System.out.println("Operation parameter: " + parameterExists.getName() + " change name to: "
									+ parameter.getName());
							hasChanges = true;
							parameterExists.setName(parameter.getName());
							addChangeToHistory(model, parameterExists.getId(), parameterExists,
									HistoryChangeType.CHANGE, "name", parameterExists.getName());
						}
//						if (!parameterExists.getIdType().toString().equals(parameter.getType())) {
//							// change type
//							System.out.println("Operation parameter: " + parameterExists.getIdType().toString() + " change type to: "
//									+ parameter.getType());
//						}

						if (hasChanges) {
							changedParameters.add(parameterExists);
						}
					}
				}
			}
		}

//		diagramRepository.save(model);

		for (ClassEntity _class : changedClasses) {
			_class.setVersion(this.version);
			classRepository.save(_class);
		}

		for (AttributeEntity attribute : changedAttributes) {
			attribute.setVersion(this.version);
			attributeRepository.save(attribute);
		}

		for (OperationEntity operation : changedOperations) {
			operation.setVersion(this.version);
			operationRepository.save(operation);
		}

		for (OperationParameterEntity parameter : changedParameters) {
			parameter.setVersion(this.version);
			parameterRepository.save(parameter);
		}
	}

	public void alterClass() {
		// class (name, extends)

	}

	public void alterAttribute() {
		// attribute (name, type, visibility)

	}

	public void alterOperation() {
		// operation (return, name, type?, visibility)

	}

	public void alterParameter() {
		// parameter (name, type, value default)

	}

	public static UMLElement findById(ArrayList<UMLElement> elements, String id) {
		UMLElement element = null;

		for (UMLElement el : elements) {
			if (el.getId().equals(id)) {
				element = el;
				break;
			}
		}

		return element;
	}

	public static ClassEntity findClassById(List<ClassEntity> classes, String id) {
		ClassEntity classFind = null;
		if (classes == null || classes.isEmpty()) {
			return classFind;
		}

		for (ClassEntity _class : classes) {
			if (_class.getIdUml().equals(id)) {
				classFind = _class;
				break;
			}
		}

		return classFind;
	}

	public static AttributeEntity findAttributeById(ClassEntity _class, String attributeId) {
		AttributeEntity attributeFind = null;
		if (_class == null) {
			return attributeFind;
		}

		for (AttributeEntity attribute : _class.getAttributes()) {
			if (attribute.getIdUml().equals(attributeId)) {
				attributeFind = attribute;
				break;
			}
		}

		return attributeFind;
	}

	public static OperationEntity findOperationById(ClassEntity _class, String operationId) {
		OperationEntity operationFind = null;
		if (_class == null) {
			return operationFind;
		}

		for (OperationEntity operation : _class.getOperations()) {
			if (operation.getIdUml().equals(operationId)) {
				operationFind = operation;
				break;
			}
		}

		return operationFind;
	}

	public static OperationParameterEntity findParameterById(OperationEntity operation, String parameterId) {
		OperationParameterEntity parameterFind = null;
		if (operation == null) {
			return parameterFind;
		}

		for (OperationParameterEntity parameter : operation.getParameters()) {
			if (parameter.getIdUml().equals(parameterId)) {
				parameterFind = parameter;
				break;
			}
		}

		return parameterFind;
	}

	public ClassEntity classStructureToEntity(ClassStructure _class, DiagramEntity diagram) {
		ClassEntity classEntity = new ClassEntity(_class.getId(), diagram, _class.getName(), this.version);

		for (ClassAttribute attribute : _class.getAttributes()) {
			classEntity.addAttribute(classAttributeToEntity(attribute, classEntity));
		}

		for (ClassOperation operation : _class.getOperations()) {
			classEntity.addOperation(classOperationToEntity(operation, classEntity));
		}

		return classEntity;
	}

	public AttributeEntity classAttributeToEntity(ClassAttribute attribute, ClassEntity classEntity) {
		return new AttributeEntity(attribute.getId(), classEntity, attribute.getName(), null, attribute.getVisibility(),
				this.version);
	}

	public OperationEntity classOperationToEntity(ClassOperation operation, ClassEntity classEntity) {
		OperationEntity _operation = new OperationEntity(operation.getId(), classEntity, null, operation.getName(),
				null, operation.getVisibility(), null, this.version);

		for (OperationParameter operationParameter : operation.getParameters()) {
			_operation.addParameter(opParameterToEntity(operationParameter, _operation));
		}

		return _operation;
	}

	public OperationParameterEntity opParameterToEntity(OperationParameter operationParameter,
			OperationEntity operation) {
		return new OperationParameterEntity(operationParameter.getId(), operation, operationParameter.getName(), null,
				null, this.version);
	}
}
