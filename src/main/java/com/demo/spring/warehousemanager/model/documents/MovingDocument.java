package com.demo.spring.warehousemanager.model.documents;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Component
public class MovingDocument extends BasicDocument {
    private String fromWarehouse;
    private String toWarehouse;
    private List<Map<String, String>> products;

    public String getFromWarehouse() {
        return fromWarehouse;
    }

    public void setFromWarehouse(String fromWarehouse) {
        this.fromWarehouse = fromWarehouse;
    }

    public String getToWarehouse() {
        return toWarehouse;
    }

    public void setToWarehouse(String toWarehouse) {
        this.toWarehouse = toWarehouse;
    }

    public List<Map<String, String>> getProducts() {
        return products;
    }

    public void setProducts(List<Map<String, String>> products) {
        this.products = products;
    }

    public MovingDocument() {
        setDocType(DocType.MOVE);
    }

    public MovingDocument(String number, LocalDateTime date, String fromWarehouse, String toWarehouse, List<Map<String, String>> products) {
        super(number, DocType.MOVE, date);
        this.fromWarehouse = fromWarehouse;
        this.toWarehouse = toWarehouse;
        this.products = products;
    }
}
