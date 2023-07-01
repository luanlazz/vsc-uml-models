package com.vsc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vsc.demo.dao.DiagramEntity;

public interface DiagramRepository 
	extends JpaRepository<DiagramEntity, Long> {
	
	@Query("SELECT d FROM DiagramEntity d WHERE idUml = ?1")
	DiagramEntity findByUmlId(String umlId);
}
