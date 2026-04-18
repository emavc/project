package com.example.project.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.project.dto.SaleItemRequest;
import com.example.project.dto.SaleRequest;
import com.example.project.model.SaleItem;
import com.example.project.model.Product;
import com.example.project.model.Sale;
import com.example.project.repository.ProductRepository;
import com.example.project.repository.SaleRepository;

@Service
public class SaleService {

    private final SaleRepository saleRepository;
    private final ProductRepository productRepository;

    public SaleService(SaleRepository saleRepository , ProductRepository productRepository){
        this.saleRepository=saleRepository;
        this.productRepository=productRepository;

    }
      public List<Sale> getAllSales(){
            return saleRepository.findAll();
        }

        public Sale getSaleById(Long id) {
             return saleRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Sale not found with id: " + id));
        }

        public List<Sale> getTodaySales() {
    LocalDate today = LocalDate.now();
    LocalDateTime startOfDay = today.atStartOfDay();
    LocalDateTime endOfDay = today.atTime(23, 59, 59);

    return saleRepository.findBySaleDateBetween(startOfDay, endOfDay);
}

public long getTodaySalesCount() {
    return getTodaySales().size();
}

public double getTodayRevenue() {
    return getTodaySales().stream()
            .mapToDouble(Sale::getTotalAmount)
            .sum();
}

public int getTodayItemsSold() {
    return getTodaySales().stream()
            .flatMap(sale -> sale.getSaleItems().stream())
            .mapToInt(item -> item.getQuantity())
            .sum();
}

    public Sale processSale(SaleRequest saleRequest){
        Sale sale = new Sale();
        List<SaleItem> saleItems = new ArrayList<>();
        Double totalAmount =0.0;
    
    for(SaleItemRequest itemRequest: saleRequest.getItems()){
        Product product =productRepository.findByBarcode(itemRequest.getBarcode())
        .orElseThrow(()-> new RuntimeException("product not found" + itemRequest.getBarcode()));

        if(product.getQuantity()<itemRequest.getQuantity()){
            throw new RuntimeException("Not enough stock for product" + product.getName());
        }
        Double subtotal = product.getSellingPrice() * itemRequest.getQuantity();

        SaleItem saleItem = new SaleItem();
        saleItem.setProduct(product);
        saleItem.setQuantity(itemRequest.getQuantity());
        saleItem.setUnitPrice(product.getSellingPrice());
        saleItem.setSubtotal(subtotal);
        saleItem.setSale(sale);

        saleItems.add(saleItem);

            product.setQuantity(product.getQuantity() - itemRequest.getQuantity());
            productRepository.save(product);

            totalAmount += subtotal;
        }

        sale.setSaleItems(saleItems);
        sale.setTotalAmount(totalAmount);
        Double amountPaid = saleRequest.getAmountPaid();
        Double balance = amountPaid - totalAmount;

       sale.setAmountPaid(amountPaid);
       sale.setBalance(balance);

        return saleRepository.save(sale);

    }
    }

