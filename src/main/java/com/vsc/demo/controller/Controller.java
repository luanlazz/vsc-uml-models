package com.vsc.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vsc.demo.dao.HistoryChangeEntity;
import com.vsc.demo.dao.VersionEntity;
import com.vsc.demo.repository.VersionRepository;
import com.vsc.demo.service.PropagateUML;
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
	private VersionRepository versionRepository;
	@Autowired
	private VSCUMLModel vscService;
	@Autowired
	private PropagateUML propagateUML;

	@PostMapping(value = "/uml")
	public int send(@RequestParam String umlContent, @RequestParam String version, @RequestParam String username) {
		try {
			VersionEntity lastVersion = this.versionRepository.findTopByOrderByIdDesc();
			if (lastVersion != null && !lastVersion.getToken().equalsIgnoreCase(version)) {
				throw new Exception("The current version is out of date, please update to the latest version.");
			}
			
			UMLModel classDiagram = UMLModelReaderService.classDiagramReader(umlContent);
			if (classDiagram == null) {
				throw new Exception("Class diagram not valid.");
			}

			System.out.println("Analising UML\n");
			List<HistoryChangeEntity> historyChanges = vscService.versionControlUml(classDiagram, lastVersion == null);
			System.out.println("\nFinish UML\n");
				
			if (!historyChanges.isEmpty()) {
				this.propagateUML.propagate(historyChanges.get(0).getVersion().getToken());
			}
						
			return HttpServletResponse.SC_OK;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return HttpServletResponse.SC_NOT_FOUND;
	}
}
