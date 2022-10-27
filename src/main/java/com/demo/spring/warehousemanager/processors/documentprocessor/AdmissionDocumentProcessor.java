package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.Product;
import com.demo.spring.warehousemanager.model.Storage;
import com.demo.spring.warehousemanager.model.documents.AdmissionDocument;
import com.demo.spring.warehousemanager.repositories.ProductRepository;
import com.demo.spring.warehousemanager.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
class AdmissionDocumentProcessor {
    @Autowired
    StorageRepository storageRepository;

    @Autowired
    ProductRepository productRepository;

    void processDocument(AdmissionDocument admissionDocument) throws IllegalArgumentException {
        admissionDocument.getProducts().forEach(
                product -> {
                    String warehouse = admissionDocument.getWarehouse();
                    long vendorCode = Long.parseLong(product.get("vendorCode").toString());
                    Storage storage = storageRepository.findByVendorCodeAndWarehouse(vendorCode, warehouse);
                    if (storage == null) addNewProduct(product, storage,warehouse, vendorCode);

                    else editExistingProduct(product, storage, vendorCode);
                }
        );
    }

    private void addNewProduct(Map product, Storage storage, String warehouse, long vendorCode) {
        long quantity = Long.parseLong(product.get("quantity").toString());
        storage.setStock(quantity);
        storage.setWarehouse(warehouse);
        storage.setVendorCode(vendorCode);
        storageRepository.save(storage);
    };

    private void editExistingProduct(Map product, Storage storage, long vendorCode ) {
        long quantity = Long.parseLong(product.get("quantity").toString());
        storage.setStock(storage.getStock() + quantity);
        storageRepository.save(storage);
        Product stockProduct = productRepository.findById(vendorCode).get();
            stockProduct.setPurchasePrice(new BigDecimal(product.get("purchasePrice").toString()));
            productRepository.save(stockProduct);
    }

}
