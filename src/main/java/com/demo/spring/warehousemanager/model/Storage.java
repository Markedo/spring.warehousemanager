package com.demo.spring.warehousemanager.model;

import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity
@Table(name = "storage")
public class Storage {

    @Id
    @NonNull
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long storageId;
    @NonNull
    private Long vendorCode;
    private String warehouse;
    private long stock;

    public Storage() {
    }

    public Storage(@NonNull Long vendorCode, String warehouseName, long stock) {
        this.vendorCode = vendorCode;
        this.warehouse = warehouseName;
        this.stock = stock;
    }

    public Long getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(@NonNull Long vendorCode) {
        this.vendorCode = vendorCode;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public long getStock() {
        return stock;
    }

    public void setStock(long stock) {
        this.stock = stock;
    }
}
