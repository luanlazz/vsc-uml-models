package com.vsc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vsc.demo.dao.AttributeEntity;

public interface AttributeRepository 
	extends JpaRepository<AttributeEntity, Long> {
}
