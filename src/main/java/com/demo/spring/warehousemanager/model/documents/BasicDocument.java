package com.demo.spring.warehousemanager.model.documents;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;

public abstract class BasicDocument {

    private long number;

    private DocType docType;

    private LocalDateTime date;

    public BasicDocument() {
    }

    public BasicDocument(long number, DocType docType, LocalDateTime date) {
        this.number = number;
        this.docType = docType;
        this.date = date;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public DocType getDocType() {
        return docType;
    }

    void setDocType(DocType docType) {
        this.docType = docType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

}
