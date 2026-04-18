package com.example.project.controller;

import com.example.project.dto.RestockRequest;
import com.example.project.model.Product;
import com.example.project.model.RestockRecord;
import com.example.project.services.StockService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock")
@CrossOrigin(origins = "*")
public class StockController {

    private final StockService stockService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/restock")
    public Map<String, Object> restockProduct(@RequestBody RestockRequest request) {
        Product updatedProduct = stockService.restockProduct(request);

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Product restocked successfully");
        response.put("product", updatedProduct);
        return response;
    }

    @GetMapping("/history")
    public List<RestockRecord> getRestockHistory() {
        return stockService.getAllRestockHistory();
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return stockService.getAllProducts();
    }

    @GetMapping("/search")
    public List<Product> searchProducts(@RequestParam(required = false) String keyword) {
        return stockService.searchProducts(keyword);
    }
}