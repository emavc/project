package com.example.project.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project.model.Sale;

public interface SaleRepository extends JpaRepository<Sale, Long> {
     List<Sale> findBySaleDateBetween(LocalDateTime start, LocalDateTime end);

}
