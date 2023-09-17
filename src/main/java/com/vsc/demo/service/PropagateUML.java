package com.vsc.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.vsc.demo.dao.HistoryChangeEntity;
import com.vsc.demo.dao.VersionEntity;
import com.vsc.demo.utils.HTTP;
import com.vsc.demo.utils.HTTPResponse;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
@Component("PropagateUML")
public class PropagateUML {

	private static final Logger logger = LoggerFactory.getLogger(PropagateUML.class);

	public PropagateUML() {

	}

	public void propagate(String umlVersion) {
		try {
			String[] users = { 
					"http://localhost:8081/client/uml/receive",
					"http://localhost:8082/client/uml/receive"
			};

//			String umlVersion = versionEntity.getId().toString();

			Map<String, String> parameters = new HashMap<>();
			parameters.put("umlVersion", umlVersion);
			
			logger.info("Propagating version: " + umlVersion);

			for (String urlUser : users) {
				HTTPResponse httpResponse = HTTP.sendRequest(urlUser, parameters);
				
				logger.info("Propagated to: " + urlUser);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
