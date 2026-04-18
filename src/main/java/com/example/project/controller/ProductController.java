package com.example.project.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.model.Product;
import com.example.project.services.ProductService;
import java.util.List;

@RestController
@RequestMapping("/api/products/")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService=productService;
    }
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product){
        return productService.saveProduct(product);
    }
    @GetMapping("/all")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }
    
    @GetMapping("/barcode/{barcode}")
    public Product getByBarcode(@PathVariable String barcode) {
        return productService.getProductByBarcode(barcode)
                .orElse(null);}

         @GetMapping("/code/{code}")
    public Product getByCode(@PathVariable String code) {
        return productService.getProductByProductCode(code)
                .orElse(null);
    }
    @GetMapping("/count")
public long getTotalProducts() {
    return productService.getAllProducts().size();
}

@GetMapping("/low-stock")
public long getLowStockProducts() {
    return productService.getAllProducts()
            .stream()
            .filter(p -> p.getQuantity() <= p.getLowStockLevel())
            .count();
}
@GetMapping("/low-stock/list")
public List<Product> getLowStockListProducts() {
    return productService.getLowStockProducts();
}
@DeleteMapping("/delete/{id}")
public String deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return "Product deleted successfully.";
}
@PutMapping("/update/{id}")
public Product updateProduct(@PathVariable Long id, @RequestBody Product updatedProduct) {
    return productService.updateProduct(id, updatedProduct);
}

@GetMapping("/{id}")
public Product getProductById(@PathVariable Long id) {
    return productService.getProductById(id);
}
    }


