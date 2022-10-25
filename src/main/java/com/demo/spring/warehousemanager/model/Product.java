package com.demo.spring.warehousemanager.model;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {
    @Id
    @NonNull
    private long vendorCode;
    private String name;
    private BigDecimal purchasePrice;
    private BigDecimal sellingPrice;

    public Product() {
    }

    public Product(@NonNull long vendorCode, String name, BigDecimal purchasePrice, BigDecimal sellingPrice) {
        this.vendorCode = vendorCode;
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.sellingPrice = sellingPrice;
    }

    public long getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(@NonNull long vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public BigDecimal getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(BigDecimal sellingPrice) {
        this.sellingPrice = sellingPrice;
    }
}
