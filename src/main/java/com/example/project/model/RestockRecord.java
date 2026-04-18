package com.example.project.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "restock_records")
public class RestockRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productCode;
    private String productName;
    private int quantityAdded;
    private String note;
    private LocalDateTime restockedAt;

    public RestockRecord() {
    }

    public RestockRecord(String productCode, String productName, int quantityAdded, String note, LocalDateTime restockedAt) {
        this.productCode = productCode;
        this.productName = productName;
        this.quantityAdded = quantityAdded;
        this.note = note;
        this.restockedAt = restockedAt;
    }

    public Long getId() {
        return id;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantityAdded() {
        return quantityAdded;
    }

    public void setQuantityAdded(int quantityAdded) {
        this.quantityAdded = quantityAdded;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public LocalDateTime getRestockedAt() {
        return restockedAt;
    }

    public void setRestockedAt(LocalDateTime restockedAt) {
        this.restockedAt = restockedAt;
    }
}
