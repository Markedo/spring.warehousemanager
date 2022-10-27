package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.documents.AdmissionDocument;
import com.demo.spring.warehousemanager.model.documents.BasicDocument;
import com.demo.spring.warehousemanager.model.documents.LoggedDocument;
import com.demo.spring.warehousemanager.model.documents.SellingDocument;
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
    LoggedDocumetRepository loggedDocumetRepository;

    public void processSellingDoc(SellingDocument sellingDocument) throws IllegalArgumentException {
        sellingDocumentProcessor.processDocument(sellingDocument);
        logDocument(sellingDocument, sellingDocument.getProducts().toString());
    }

    public void processAdmissionDoc(AdmissionDocument admissionDocument) throws IllegalArgumentException {
        admissionDocumentProcessor.processDocument(admissionDocument);
        logDocument(admissionDocument, admissionDocument.getProducts().toString());
    }

    private void logDocument(BasicDocument document, String docData) {
        LoggedDocument loggedDocument = new LoggedDocument(
                document.getNumber(),
                document.getDocType(),
                document.getDate(),
                docData);
        loggedDocumetRepository.save(loggedDocument);
    }

}
