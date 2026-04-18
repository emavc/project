package com.example.project.dto;


import java.time.LocalDateTime;
import java.util.List;

public class RecieptResponse {

    private Long saleId;
    private LocalDateTime saleDate;
    private Double totalAmount;
    private Double amountPaid;
    private Double balance;
    private List<RecieptItemResponse> items;

    public RecieptResponse() {
    }

    public RecieptResponse(Long saleId, LocalDateTime saleDate, Double totalAmount, Double amountPaid, Double balance, List<RecieptItemResponse> items) {
        this.saleId = saleId;
        this.saleDate = saleDate;
        this.totalAmount = totalAmount;
        this.amountPaid=amountPaid;
        this.balance=balance;
        this.items = items;
    }

    public Long getSaleId() {
        return saleId;
    }

    public void setSaleId(Long saleId) {
        this.saleId = saleId;
    }

    public LocalDateTime getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDateTime saleDate) {
        this.saleDate = saleDate;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }
    public Double getAmountPaid(){
        return amountPaid;
    }
    public void setAmountPaid(Double amountpaid){
        this.amountPaid= amountpaid;
    }
    public Double getBalance(){
        return balance;
    }
    public void setBalance(Double balance){
        this.balance=balance;
    }

    public List<RecieptItemResponse> getItems() {
        return items;
    }

    public void setItems(List<RecieptItemResponse> items) {
        this.items = items;
    }
}