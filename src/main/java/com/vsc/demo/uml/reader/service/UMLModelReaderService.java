package com.vsc.demo.uml.reader.service;

import java.io.File;

import org.eclipse.emf.common.util.URI;
import org.eclipse.uml2.uml.Package;
import org.eclipse.uml2.uml.resource.UMLResource;

import com.vsc.demo.uml.loader.ModelLoader;
import com.vsc.demo.uml.models._class.UMLModel;
import com.vsc.demo.uml.reader.diagram.UMLModelReader;

public class UMLModelReaderService {

	public static UMLModel classDiagramReader(String umlContent) throws Exception {
		Package aPackage = new ModelLoader().loadModel(umlContent);
		return UMLModelReader.getRefModelDetails(aPackage);
	}

	public static UMLModel classDiagramReader(File modelFile) throws Exception {
		Package aPackage = new ModelLoader().loadModel(modelFile);
		return UMLModelReader.getRefModelDetails(aPackage);
	}

	public static Package modelReader(String filePath) throws Exception {
		File model = new File(filePath);
		return new ModelLoader().loadModel(model);
	}
	
	public static UMLResource modelReaderUmlResource(URI filePath) throws Exception {
		File modelFile = new File(filePath.toFileString());
		return (UMLResource) new ModelLoader().registerModel(modelFile);
	}
	
	public static UMLResource modelReaderUmlResource(String umlContent) throws Exception {
		return (UMLResource) new ModelLoader().registerModel(umlContent);
	}
}
