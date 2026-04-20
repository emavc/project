package com.example.project.dto;

import java.util.List;

public class SaleRequest {

    private List<SaleItemRequest> items;
    private double amountPaid;

    public SaleRequest() {
    }

    public List<SaleItemRequest> getItems() {
        return items;
    }

    public void setItems(List<SaleItemRequest> items) {
        this.items = items;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }
}