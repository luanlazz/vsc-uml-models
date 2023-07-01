package com.vsc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vsc.demo.dao.OperationParameterEntity;

public interface OperationParameterRepository extends JpaRepository<OperationParameterEntity, Long> {
}
