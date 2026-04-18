package com.example.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.project.model.SaleItem;

public interface SaleItemRepository extends JpaRepository<SaleItem,Long> {

    
}