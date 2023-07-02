package com.vsc.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.vsc.demo.dao.AttributeEntity;
import com.vsc.demo.dao.ClassEntity;
import com.vsc.demo.dao.DiagramEntity;
import com.vsc.demo.dao.OperationEntity;
import com.vsc.demo.dao.OperationParameterEntity;
import com.vsc.demo.repository.AttributeRepository;
import com.vsc.demo.repository.ClassRepository;
import com.vsc.demo.repository.DiagramRepository;
import com.vsc.demo.repository.OperationParameterRepository;
import com.vsc.demo.repository.OperationRepository;
import com.vsc.demo.uml.models._class.ClassAttribute;
import com.vsc.demo.uml.models._class.ClassOperation;
import com.vsc.demo.uml.models._class.ClassStructure;
import com.vsc.demo.uml.models._class.OperationParameter;
import com.vsc.demo.uml.models._class.UMLModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
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

	// Save a new model, read all elements and persist
	public void versionControlUml(UMLModel newModel) {
		DiagramEntity model = this.loadModelById(newModel.getId());
		if (model == null) {
			model = this.saveNewModel(newModel);
		} else {
			model = this.saveChanges(model, newModel);
		}
	}

	public DiagramEntity saveNewModel(UMLModel model) {
		DiagramEntity diagram = new DiagramEntity(model.getId(), model.getName(), model.getType());

		for (ClassStructure _class : model.getClasses()) {
			diagram.addClass(classStrucutureToEntity(_class, diagram));
		}

		diagramRepository.save(diagram);
		System.out.println("new model save: " + diagram.getName());

		return diagram;
	}

	public DiagramEntity loadModelById(String id) {
		DiagramEntity diagramEntity = diagramRepository.findByUmlId(id);
		return diagramEntity;
	}

	public DiagramEntity saveChanges(DiagramEntity model, UMLModel newModel) {
		DiagramEntity diagramEntity = new DiagramEntity();
		this.addedClass(model, newModel);

		return diagramEntity;
	}

	public void addedClass(DiagramEntity model, UMLModel newModel) {
		List<ClassEntity> addClasses = new ArrayList<ClassEntity>();
		List<AttributeEntity> addAttributes = new ArrayList<AttributeEntity>();
		List<OperationEntity> addOperations = new ArrayList<OperationEntity>();
		List<OperationParameterEntity> addParameters = new ArrayList<OperationParameterEntity>();

		for (ClassStructure _class : newModel.getClasses()) {
			ClassEntity classExists = findClassById(model.getClasses(), _class.getId());
			if (classExists == null) {
				addClasses.add(classStrucutureToEntity(_class, model));
				break;
			}

			for (ClassAttribute attribute : _class.getAttributes()) {
				AttributeEntity attributeExists = findAttributeById(classExists, attribute.getId());
				if (attributeExists == null) {
					addAttributes.add(classAttributeToEntity(attribute, classExists));
				}
			}

			for (ClassOperation operation : _class.getOperations()) {
				OperationEntity operationExists = findOperationById(classExists, operation.getId());
				if (operationExists == null) {
					addOperations.add(classOperationToEntity(operation, classExists));
					break;
				}

				for (OperationParameter parameter : operation.getParameters()) {
					OperationParameterEntity parameterExists = findParameterById(operationExists, parameter.getId());
					if (parameterExists == null) {
						addParameters.add(opParameterToEntity(parameter, operationExists));
					}
				}
			}
		}

		for (ClassEntity _class : addClasses) {
			classRepository.save(_class);
			System.out.println("new class: " + _class.getName());
		}

		for (AttributeEntity attribute : addAttributes) {
			attributeRepository.save(attribute);
			System.out.println("new attribute: " + attribute.getName());
		}

		for (OperationEntity operation : addOperations) {
			operationRepository.save(operation);
			System.out.println("new operation: " + operation.getName());
		}

		for (OperationParameterEntity parameter : addParameters) {
			parameterRepository.save(parameter);
			System.out.println("new parameter: " + parameter.getName());
		}
	}

	public void removedClass() {

	}

	public void removedAttributes() {

	}

	public void removedOperations() {

	}

	public void removedParameters() {

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

	public static ClassEntity findClassById(List<ClassEntity> classes, String id) {
		ClassEntity classFind = null;

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

		for (OperationParameterEntity parameter : operation.getParameters()) {
			if (parameter.getIdUml().equals(parameterId)) {
				parameterFind = parameter;
				break;
			}
		}

		return parameterFind;
	}

	public static ClassEntity classStrucutureToEntity(ClassStructure _class, DiagramEntity diagram) {
		ClassEntity classEntity = new ClassEntity(_class.getId(), diagram, _class.getName());

		for (ClassAttribute attribute : _class.getAttributes()) {
			classEntity.addAttribute(classAttributeToEntity(attribute, classEntity));
		}

		for (ClassOperation operation : _class.getOperations()) {
			classEntity.addOperation(classOperationToEntity(operation, classEntity));
		}

		return classEntity;
	}

	public static AttributeEntity classAttributeToEntity(ClassAttribute attribute, ClassEntity classEntity) {
		return new AttributeEntity(attribute.getId(), classEntity, attribute.getName(), null,
				attribute.getVisibility());
	}

	public static OperationEntity classOperationToEntity(ClassOperation operation, ClassEntity classEntity) {
		OperationEntity _operation = new OperationEntity(operation.getId(), classEntity, null, operation.getName(),
				null, operation.getVisibility(), null);

		for (OperationParameter operationParameter : operation.getParameters()) {
			_operation.addParameter(opParameterToEntity(operationParameter, _operation));
		}

		return _operation;
	}

	public static OperationParameterEntity opParameterToEntity(OperationParameter operationParameter,
			OperationEntity operation) {
		return new OperationParameterEntity(operationParameter.getId(), operation, operationParameter.getName(), null,
				null);
	}
}
