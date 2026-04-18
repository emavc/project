package com.example.project.repository;

import com.example.project.model.RestockRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestockRecordRepository extends JpaRepository<RestockRecord, Long> {
}