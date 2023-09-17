package com.vsc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vsc.demo.dao.DiagramEntity;
import com.vsc.demo.dao.VersionEntity;

public interface VersionRepository extends JpaRepository<VersionEntity, Long> {
	@Query("SELECT v FROM VersionEntity v WHERE id = ?1")
	DiagramEntity findByUmlId(Long id);
	
	VersionEntity findTopByOrderByIdDesc();
}
