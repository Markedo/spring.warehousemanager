package com.demo.spring.warehousemanager.model.documents;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Table(name = "documents")
public class BasicDocument {
    @Id
    private long number;

    private DocType docType;

    private LocalDateTime date;

    @ElementCollection
    private Map<String, String> docData;

    public BasicDocument() {
    }

    public BasicDocument(long number, DocType docType, LocalDateTime date, Map<String, String> data) {
        this.number = number;
        this.docType = docType;
        this.date = date;
        this.docData = data;
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

    public void setDocType(DocType docType) {
        this.docType = docType;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Map<String, String> getData() {
        return docData;
    }

    public void setData(Map<String, String> data) {
        this.docData = data;
    }
}
