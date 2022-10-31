package com.demo.spring.warehousemanager.model.documents;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BasicDocument {
    @Id
    private String number;

    private DocType docType;

    private LocalDateTime date;

    public BasicDocument() {
    }

    public BasicDocument(String number, DocType docType, LocalDateTime date) {
        this.number = number;
        this.docType = docType;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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
