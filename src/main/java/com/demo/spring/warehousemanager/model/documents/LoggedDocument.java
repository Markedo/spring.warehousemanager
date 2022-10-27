package com.demo.spring.warehousemanager.model.documents;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
@Entity
@Table(name = "documents_log")
public class LoggedDocument extends BasicDocument {

    @Id
    private long number;

    private DocType docType;

    private LocalDateTime date;

    private String docData;

    public LoggedDocument() {
    }

    public LoggedDocument(long number, DocType docType, LocalDateTime date, String data) {
        super(number, docType, date);
        this.docData = data;
    }

    public String getData() {
        return docData;
    }

    public void setData(String data) {
        this.docData = data;
    }

    public void setDocType(DocType docType) {
        this.docType = docType;
    }
}
