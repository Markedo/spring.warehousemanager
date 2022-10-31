package com.demo.spring.warehousemanager.model.documents;

import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "documents_log")
public class LoggedDocument extends BasicDocument {

    private String docData;

    public LoggedDocument() {
    }

    public LoggedDocument(String number, DocType docType, LocalDateTime date, String docData) {
        super(number, docType, date);
        this.docData = docData;
    }

    public String getData() {
        return docData;
    }

    public void setData(String data) {
        this.docData = data;
    }
}
