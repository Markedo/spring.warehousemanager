package com.demo.spring.warehousemanager.model;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "storage")
public class Storage {

    @Id
    @GeneratedValue
    private long storageId;

    @NonNull
    private long vendorCode;
    private String warehouse;
    private long stock;

    public Storage() {
    }

    public Storage(@NonNull long vendorCode, String warehouseName, long stock) {
        this.vendorCode = vendorCode;
        this.warehouse = warehouseName;
        this.stock = stock;
    }

    public long getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(@NonNull long vendorCode) {
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
