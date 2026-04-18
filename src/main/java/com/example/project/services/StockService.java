package com.example.project.services;

import com.example.project.dto.RestockRequest;
import com.example.project.model.Product;
import com.example.project.model.RestockRecord;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.RestockRecordRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    private final ProductRepository productRepository;
    private final RestockRecordRepository restockRecordRepository;

    public StockService(ProductRepository productRepository, RestockRecordRepository restockRecordRepository) {
        this.productRepository = productRepository;
        this.restockRecordRepository = restockRecordRepository;
    }

    public Product restockProduct(RestockRequest request) {
        if (request.getProductCode() == null || request.getProductCode().trim().isEmpty()) {
            throw new RuntimeException("Product code is required");
        }

        if (request.getQuantity() <= 0) {
            throw new RuntimeException("Quantity must be greater than 0");
        }

        Product product = productRepository.findByProductCode(request.getProductCode())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        int currentQty = product.getQuantity();
        product.setQuantity(currentQty + request.getQuantity());

        Product savedProduct = productRepository.save(product);

        RestockRecord record = new RestockRecord(
                savedProduct.getProductCode(),
                savedProduct.getName(),
                request.getQuantity(),
                request.getNote(),
                LocalDateTime.now()
        );

        restockRecordRepository.save(record);

        return savedProduct;
    }

    public List<RestockRecord> getAllRestockHistory() {
        return restockRecordRepository.findAll();
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> searchProducts(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return productRepository.findAll();
        }

        String search = keyword.trim();

        List<Product> results = new ArrayList<>();

        results.addAll(productRepository.findByNameContainingIgnoreCase(search));
        results.addAll(productRepository.findByCategoryContainingIgnoreCase(search));
        results.addAll(productRepository.findByProductCodeContainingIgnoreCase(search));
        results.addAll(productRepository.findByBarcodeContainingIgnoreCase(search));

        List<Product> uniqueResults = new ArrayList<>();
        for (Product product : results) {
            boolean exists = false;
            for (Product p : uniqueResults) {
                if (p.getId().equals(product.getId())) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                uniqueResults.add(product);
            }
        }

        return uniqueResults;
    }
}