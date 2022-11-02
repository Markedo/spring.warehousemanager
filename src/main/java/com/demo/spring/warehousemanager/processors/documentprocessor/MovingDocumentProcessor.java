package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.Storage;
import com.demo.spring.warehousemanager.model.documents.MovingDocument;
import com.demo.spring.warehousemanager.repositories.ListedProductRepository;
import com.demo.spring.warehousemanager.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
class MovingDocumentProcessor {
    @Autowired
    StorageRepository storageRepository;

    @Autowired
    ListedProductRepository listedProductRepository;

    void processDocument(MovingDocument movingDocument) throws IllegalArgumentException {
        List<Storage> editedStorages = Collections.synchronizedList(new LinkedList<Storage>());
        List<Storage> nullifiedStorages = Collections.synchronizedList(new LinkedList<Storage>());

        String fromWarehouse = movingDocument.getFromWarehouse();
        String toWarehouse = movingDocument.getToWarehouse();

        movingDocument.getProducts().forEach(
                product -> {
                    Long vendorCode = Long.parseLong(product.get("vendorCode"));
                    Storage storageFrom = storageRepository.findByVendorCodeAndWarehouse(vendorCode, fromWarehouse);
                    if (storageFrom == null)
                        throw new IllegalArgumentException("Can't find product with provided vendor code in target warehouse.");

                    long quantity = Long.parseLong(product.get("quantity"));
                    long editedStocks = storageFrom.getStock() - quantity;
                    if (editedStocks < 0)
                        throw new IllegalArgumentException("Provided quantity exceeding stocks.");
                    else {
                        storageFrom.setStock(editedStocks);
                        editedStorages.add(storageFrom);
                    }
                    if (editedStocks == 0) nullifiedStorages.add(storageFrom);

                    Storage storageTo = storageRepository.findByVendorCodeAndWarehouse(vendorCode, toWarehouse);
                    if (storageTo == null) {
                        storageTo = new Storage(vendorCode, toWarehouse, quantity);
                    }
                    else {
                       storageTo.setStock(storageTo.getStock() + quantity);
                    }
                    editedStorages.add(storageTo);

                }
        );
        storageRepository.saveAll(editedStorages);
        storageRepository.deleteAll(nullifiedStorages);
    }
}
