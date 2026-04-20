package com.example.project.controller;

import com.example.project.services.SaleService;
import com.example.project.dto.RecieptItemResponse;
import com.example.project.dto.RecieptResponse;
import com.example.project.dto.SaleRequest;
import com.example.project.model.Sale;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/sales")
public class SaleController {

    private final SaleService saleService;

    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @PostMapping("/complete")
    public RecieptResponse completeSale(@RequestBody SaleRequest saleRequest) {
        Sale savedSale = saleService.processSale(saleRequest);
        return mapToReceiptResponse(savedSale);
    }

    @GetMapping("/all")
    public List<RecieptResponse> getAllSales() {
        List<Sale> sales = saleService.getAllSales();
        return sales.stream()
                .map(this::mapToReceiptResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public RecieptResponse getSaleById(@PathVariable Long id) {
        Sale sale = saleService.getSaleById(id);
        return mapToReceiptResponse(sale);
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

    private RecieptResponse mapToReceiptResponse(Sale sale) {
        List<RecieptItemResponse> items = sale.getSaleItems().stream()
                .map(item -> new RecieptItemResponse(
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .collect(Collectors.toList());

        return new RecieptResponse(
                sale.getId(),
                sale.getSaleDate(),
                sale.getTotalAmount(),
                sale.getAmountPaid(),
                sale.getBalance(),
                items
        );
    }
}