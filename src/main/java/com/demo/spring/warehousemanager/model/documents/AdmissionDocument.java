package com.demo.spring.warehousemanager.model.documents;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class AdmissionDocument extends BasicDocument {
    private String warehouse;
    private List<Map> products;

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public List<Map> getProducts() {
        return products;
    }

    public void setProducts(List<Map> products) {
        this.products = products;
    }

    public AdmissionDocument() {
        setDocType(DocType.ADMISSION);
    }

    public AdmissionDocument(long number, LocalDateTime date, Map<String, String> data, String warehouse, List<Map> products) {
        super(number, DocType.ADMISSION, date);
        this.warehouse = warehouse;
        this.products = products;
    }
}
