package com.example.project.repository;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.project.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional <Product> findByBarcode(String barcode);
    Optional <Product> findByProductCode(String productcode);

    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategoryContainingIgnoreCase(String category);
    List<Product> findByProductCodeContainingIgnoreCase(String productCode);
    List<Product> findByBarcodeContainingIgnoreCase(String barcode);
}
