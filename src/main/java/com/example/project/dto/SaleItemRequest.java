package com.example.project.dto;

public class SaleItemRequest {

    private String barcode;
    private Integer quantity;

    public SaleItemRequest(){

    }
    public String getBarcode(){
        return barcode;
    }
    public void setBarcode(String barcode){
        this.barcode=barcode;
    }
    public Integer getQuantity(){
        return quantity;
    }
    public void setQuantity(Integer quantity){
        this.quantity=quantity;
    }
    
}
