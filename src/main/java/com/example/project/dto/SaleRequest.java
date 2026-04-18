package com.example.project.dto;

import java.util.List;

public class SaleRequest {

    private List<SaleItemRequest> items;
    private Double amountPaid;

    public SaleRequest() {
    }

    public List<SaleItemRequest> getItems() {
        return items;
    }

    public void setItems(List<SaleItemRequest> items) {
        this.items = items;
    }

    public Double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(Double amountPaid) {
        this.amountPaid = amountPaid;
    }
}