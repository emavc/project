package com.example.project.controller;

import com.example.project.services.SaleService;
import com.example.project.dto.RecieptItemResponse;
import com.example.project.dto.RecieptResponse;

import java.util.stream.Collectors;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.SaleRequest;
import com.example.project.model.Sale;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;
    
    public SaleController(SaleService saleService){
        this.saleService = saleService;
    }
    @PostMapping("/complete")
    public RecieptResponse completeSale(@RequestBody SaleRequest saleRequest){
        Sale savedSale = saleService.processSale(saleRequest);

         List<RecieptItemResponse> items = savedSale.getSaleItems().stream()
                .map(item -> new RecieptItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        return new RecieptResponse(
                savedSale.getId(),
                savedSale.getSaleDate(),
                savedSale.getTotalAmount(),
                savedSale.getAmountPaid(),
                savedSale.getBalance(),
                items
        );
    }
    @GetMapping("/all")
public List<RecieptResponse> getAllSales() {
    List<Sale> sales = saleService.getAllSales();

    return sales.stream().map(sale -> {
        List<RecieptItemResponse> items = sale.getSaleItems().stream()
                .map(item -> new RecieptItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .toList();

        return new RecieptResponse(
                sale.getId(),
                sale.getSaleDate(),
                sale.getTotalAmount(),
                sale.getAmountPaid(),
                sale.getBalance(),
                items
        );
    }).toList();  
}
 @GetMapping("/{id}")

    public RecieptResponse getSaleById(@PathVariable Long id) {
    Sale sale = saleService.getSaleById(id);

    List<RecieptItemResponse> items = sale.getSaleItems().stream()
            .map(item -> new RecieptItemResponse(
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getSubtotal()
            ))
            .toList();

    return new RecieptResponse(
            sale.getId(),
            sale.getSaleDate(),
            sale.getTotalAmount(),
            sale.getAmountPaid(),
            sale.getBalance(),
            items
    );
}
@GetMapping("/today/count")
public long getTodaySalesCount() {
    return saleService.getTodaySalesCount();
}

@GetMapping("/today/revenue")
public double getTodayRevenue() {
    return saleService.getTodayRevenue();
}

@GetMapping("/today/items")
public int getTodayItemsSold() {
    return saleService.getTodayItemsSold();
}
    
}
