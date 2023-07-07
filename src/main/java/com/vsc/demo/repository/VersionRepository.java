package com.vsc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vsc.demo.dao.VersionEntity;

public interface VersionRepository extends JpaRepository<VersionEntity, Long> {
}
