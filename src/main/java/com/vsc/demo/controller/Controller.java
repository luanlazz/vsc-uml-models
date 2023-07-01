package com.vsc.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vsc.demo.service.VSCUMLModel;
import com.vsc.demo.uml.models._class.UMLModel;
import com.vsc.demo.uml.reader.service.UMLModelReaderService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/server")
public class Controller {

	private static final Logger logger = LoggerFactory.getLogger(Controller.class);

	@Autowired
	private VSCUMLModel service;

	@PostMapping(value = "/uml")
	public int send(@RequestParam String filePath) {
		try {
			UMLModel classDiagram = UMLModelReaderService.classDiagramReader(filePath);
			if (classDiagram == null) {
				throw new Exception("Diagrama de classes não encontrado.");
			}

			service.versionControlUml(classDiagram);
			return HttpServletResponse.SC_OK;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return HttpServletResponse.SC_NOT_FOUND;
	}
}
