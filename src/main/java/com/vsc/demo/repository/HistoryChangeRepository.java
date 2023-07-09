package com.vsc.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vsc.demo.dao.HistoryChangeEntity;

public interface HistoryChangeRepository extends JpaRepository<HistoryChangeEntity, Long> {
}
