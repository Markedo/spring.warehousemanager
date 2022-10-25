package com.demo.spring.warehousemanager.model.documents;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class SellingDocument extends BasicDocument {
    private String warehouse;
    private List<Product> products;

    public record Product(String vendorCode, long quantity, BigDecimal sellingPrice) {
    };

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public SellingDocument() {
    }

    public SellingDocument(long number, LocalDateTime date, Map<String, String> data, String warehouse, List<Product> products) {
        super(number, DocType.SELL, date, data);
        this.warehouse = warehouse;
        this.products = products;
    }
}
