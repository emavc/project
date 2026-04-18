package com.example.project.services;

import java.util.Optional;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project.model.Product;
import com.example.project.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository=productRepository;
    }
    public Product saveProduct(Product product){
        return productRepository.save(product);
    }
    
    public List <Product> getAllProducts(){
        return productRepository.findAll();
    }
    public Optional <Product> getProductByBarcode(String barcode){
        return productRepository.findByBarcode(barcode);
    }

    public Optional <Product> getProductByProductCode(String productcode){
        return productRepository.findByProductCode(productcode);
    }

    public List<Product> getLowStockProducts() {
    return productRepository.findAll()
            .stream()
            .filter(product -> product.getQuantity() <= product.getLowStockLevel())
            .toList();
    }

     public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    public Product updateProduct(Long id, Product updatedProduct) {
    Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));

    existingProduct.setName(updatedProduct.getName());
    existingProduct.setProductCode(updatedProduct.getProductCode());
    existingProduct.setBarcode(updatedProduct.getBarcode());
    existingProduct.setCategory(updatedProduct.getCategory());
    existingProduct.setBuyingPrice(updatedProduct.getBuyingPrice());
    existingProduct.setSellingPrice(updatedProduct.getSellingPrice());
    existingProduct.setQuantity(updatedProduct.getQuantity());
    existingProduct.setLowStockLevel(updatedProduct.getLowStockLevel());
    existingProduct.setBaseUnit(updatedProduct.getBaseUnit());
    existingProduct.setPackageName(updatedProduct.getPackageName());
    existingProduct.setUnitsPerPackage(updatedProduct.getUnitsPerPackage());

    return productRepository.save(existingProduct);
}

public Product getProductById(Long id) {
    return productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
}

    
}
