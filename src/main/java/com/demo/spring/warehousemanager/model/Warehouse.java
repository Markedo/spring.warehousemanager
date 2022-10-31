package com.demo.spring.warehousemanager.model;

import org.springframework.lang.NonNull;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "warehouses")
public class Warehouse {
    @Id
    @NonNull
    private String warehouseCode;

    public Warehouse() {
    }

    public Warehouse( @NonNull String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(@NonNull String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }
}
