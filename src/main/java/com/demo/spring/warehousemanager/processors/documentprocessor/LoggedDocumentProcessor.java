package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.documents.*;
import com.demo.spring.warehousemanager.repositories.LoggedDocumetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LoggedDocumentProcessor {
    @Autowired
    LoggedDocumetRepository loggedDocumetRepository;
    public void logDocument(BasicDocument document, String docData) {
        switch (document.getDocType()) {
            case SELL -> docData = "warehouse="
                    + ((SellingDocument) document).getWarehouse()
                    + ", "
                    + docData;
            case ADMISSION -> docData = "warehouse="
                    + ((AdmissionDocument) document).getWarehouse()
                    + ", "
                    + docData;
            case MOVE -> docData = "fromWarehouse="
                    + ((MovingDocument) document).getFromWarehouse()
                    + ", "
                    + "toWarehouse="
                    + ((MovingDocument) document).getToWarehouse()
                    + ", "
                    + docData;
        }

        LoggedDocument loggedDocument = new LoggedDocument(
                document.getNumber(),
                document.getDocType(),
                document.getDate(),
                docData);
        loggedDocumetRepository.save(loggedDocument);
    }
}
