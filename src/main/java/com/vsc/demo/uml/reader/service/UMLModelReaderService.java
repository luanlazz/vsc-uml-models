package com.vsc.demo.uml.reader.service;

import java.io.File;

import org.eclipse.uml2.uml.Package;

import com.vsc.demo.uml.loader.ModelLoader;
import com.vsc.demo.uml.models._class.UMLModel;
import com.vsc.demo.uml.reader.diagram.UMLModelReader;

public class UMLModelReaderService {

	public static UMLModel classDiagramReader(String filePath) throws Exception {
		File model = new File(filePath);
		Package aPackage = new ModelLoader().loadModel(model);
		return UMLModelReader.getRefModelDetails(aPackage);
	}
}
