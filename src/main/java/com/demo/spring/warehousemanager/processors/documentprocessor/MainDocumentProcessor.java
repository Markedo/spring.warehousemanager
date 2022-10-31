package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.documents.*;
import com.demo.spring.warehousemanager.repositories.LoggedDocumetRepository;
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
    DocumentLogger documentLogger;

    public void processSellingDoc(SellingDocument sellingDocument) throws IllegalArgumentException {
        documentValidator.isDocumentValid(sellingDocument);
        sellingDocumentProcessor.processDocument(sellingDocument);
        documentLogger.logDocument(sellingDocument, sellingDocument.getProducts().toString());
    }

    public void processAdmissionDoc(AdmissionDocument admissionDocument) throws IllegalArgumentException {
        documentValidator.isDocumentValid(admissionDocument);
        admissionDocumentProcessor.processDocument(admissionDocument);
        documentLogger.logDocument(admissionDocument, admissionDocument.getProducts().toString());
    }

    public void processMovingDoc(MovingDocument movingDocument) throws IllegalArgumentException {
        documentValidator.isDocumentValid(movingDocument);
        movingDocumentProcessor.processDocument(movingDocument);
        documentLogger.logDocument(movingDocument, movingDocument.getProducts().toString());
    }

}
