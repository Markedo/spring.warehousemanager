package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.documents.AdmissionDocument;
import com.demo.spring.warehousemanager.model.documents.MovingDocument;
import com.demo.spring.warehousemanager.model.documents.SellingDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MainDocumentProcessor {

    @Autowired
    SellingDocumentProcessor sellingDocumentProcessor;

    @Autowired
    AdmissionDocumentProcessor admissionDocumentProcessor;

    @Autowired
    MovingDocumentProcessor movingDocumentProcessor;

    @Autowired
    DocumentValidator documentValidator;

    @Autowired
    LoggedDocumentProcessor loggedDocumentProcessor;

    public void processSellingDoc(SellingDocument sellingDocument) throws IllegalArgumentException {
        documentValidator.isDocumentValid(sellingDocument);
        sellingDocumentProcessor.processDocument(sellingDocument);
        loggedDocumentProcessor.logDocument(sellingDocument, sellingDocument.getProducts().toString());
    }

    public void processAdmissionDoc(AdmissionDocument admissionDocument) throws IllegalArgumentException {
        documentValidator.isDocumentValid(admissionDocument);
        admissionDocumentProcessor.processDocument(admissionDocument);
        loggedDocumentProcessor.logDocument(admissionDocument, admissionDocument.getProducts().toString());
    }

    public void processMovingDoc(MovingDocument movingDocument) throws IllegalArgumentException {
        documentValidator.isDocumentValid(movingDocument);
        movingDocumentProcessor.processDocument(movingDocument);
        loggedDocumentProcessor.logDocument(movingDocument, movingDocument.getProducts().toString());
    }

    public boolean validateWarehouse(String warehouse) {
        return documentValidator.isWarehouseValid(warehouse);
    }

}
