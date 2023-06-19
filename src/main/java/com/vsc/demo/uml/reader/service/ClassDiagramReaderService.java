package com.vsc.demo.uml.reader.service;

import java.io.File;

import org.eclipse.uml2.uml.Package;

import com.vsc.demo.uml.loader.ModelLoader;
import com.vsc.demo.uml.models._class.ClassDiagram;
import com.vsc.demo.uml.reader.diagram.ClassDiagramReader;

public class ClassDiagramReaderService {

	public static ClassDiagram classDiagramReader(String filePath) throws Exception {
		File model = new File(filePath);
        Package aPackage = new ModelLoader().loadModel(model);
        return ClassDiagramReader.getRefModelDetails(aPackage);
    }
}
