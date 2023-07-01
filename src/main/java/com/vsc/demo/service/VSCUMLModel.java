package com.vsc.demo.service;

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
import com.vsc.demo.uml.models._class.UMLModel;
import com.vsc.demo.uml.models._class.ClassOperation;
import com.vsc.demo.uml.models._class.ClassStructure;
import com.vsc.demo.uml.models._class.OperationParameter;

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
	public void versionControlUml(UMLModel model) {
		DiagramEntity diagramEntity = this.loadModelById(model.getId());
		if (diagramEntity == null) {
			diagramEntity = this.saveNewModel(model);
		} else {
			diagramEntity = this.saveChanges(model);
		}
	}

	public DiagramEntity saveNewModel(UMLModel model) {
		DiagramEntity diagram = new DiagramEntity(model.getId(), model.getName(), model.getType());

		for (ClassStructure classSt : model.getClasses()) {
			ClassEntity _class = new ClassEntity(classSt.getId(), diagram, classSt.getName());
			diagram.addClass(_class);

			for (ClassAttribute attribute : classSt.getAttributes()) {
				AttributeEntity _attribute = new AttributeEntity(attribute.getId(), _class, attribute.getName(), null,
						attribute.getVisibility());
				_class.addAttribute(_attribute);
			}

			for (ClassOperation operation : classSt.getOperations()) {
				OperationEntity _operation = new OperationEntity(operation.getId(), _class, null, operation.getName(),
						null, operation.getVisibility(), null);
				_class.addOperation(_operation);

				for (OperationParameter operationParameter : operation.getParameters()) {
					OperationParameterEntity _parameter = new OperationParameterEntity(operationParameter.getId(),
							_operation, operationParameter.getName(), null, null);
					_operation.addParameter(_parameter);
				}
			}
		}

		diagramRepository.save(diagram);

		return diagram;
	}

	public DiagramEntity loadModelById(String id) {
		DiagramEntity diagramEntity = diagramRepository.findByUmlId(id);
		return diagramEntity;
	}

	public DiagramEntity saveChanges(UMLModel classDiagram) {
		DiagramEntity diagramEntity = new DiagramEntity();
		return diagramEntity;
	}

	// If Model alredy axists, compare diferences
	// Added, remove or alter
	// add or remove:
	// class
	// attribute
	// operation
	// parameter
	//
	// alter:
	// class (name, extends)
	// attribute (name, type, visibility)
	// operation (return, name, type?, visibility)
	// parameter (name, type, value default)

}
