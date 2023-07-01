package com.vsc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vsc.demo.dao.ClassEntity;

public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

	@Query("SELECT c FROM ClassEntity c WHERE idUml = ?1")
	ClassEntity findByUmlId(String umlId);
}
