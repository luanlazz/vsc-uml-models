package com.vsc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vsc.demo.dao.OperationEntity;

public interface OperationRepository extends JpaRepository<OperationEntity, Long> {
}
