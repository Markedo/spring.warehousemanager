package com.demo.spring.warehousemanager.model.documents;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


public class SellingDocument extends BasicDocument {
    private String warehouse;
    private List<Map<String, String>> products;

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public List<Map<String, String>> getProducts() {
        return products;
    }

    public void setProducts(List<Map<String, String>> products) {
        this.products = products;
    }

    public SellingDocument() {
        setDocType(DocType.SELL);
    }

    public SellingDocument(String number, LocalDateTime date, Map<String, String> data, String warehouse, List<Map<String, String>> products) {
        super(number, DocType.SELL, date);
        this.warehouse = warehouse;
        this.products = products;
    }
}
