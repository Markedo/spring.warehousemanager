package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.ListedProduct;
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
        String warehouse = admissionDocument.getWarehouse();
        admissionDocument.getProducts().forEach(
                product -> {
                    Long vendorCode = Long.parseLong(product.get("vendorCode"));
                    ListedProduct stockListedProduct = new ListedProduct();
                    try {
                        stockListedProduct = productRepository.findById(vendorCode).get();
                    }
                    catch (Exception e) {}
                    if (stockListedProduct.getVendorCode() == null) {
                        addNewProduct(product, warehouse, vendorCode);
                    } else {
                        Storage storage = storageRepository.findByVendorCodeAndWarehouse(vendorCode, warehouse);
                        if (storage == null) addNewProduct(product, warehouse, vendorCode);
                        else editExistingProduct(product, storage, vendorCode);
                    }
                }
        );
    }

    private void addNewProduct(Map<String, String> product, String warehouse, Long vendorCode) {
        ListedProduct newListedProduct = new ListedProduct();
        newListedProduct.setVendorCode(vendorCode);
        newListedProduct.setName(product.get("name"));
        newListedProduct.setPurchasePrice(new BigDecimal(product.get("price")));
        newListedProduct.setSellingPrice(null);
        productRepository.save(newListedProduct);

        Storage storage = new Storage();
        storage.setWarehouse(warehouse);
        storage.setVendorCode(vendorCode);
        long quantity = Long.parseLong(product.get("quantity"));
        storage.setStock(quantity);
        storageRepository.save(storage);
    };

    private void editExistingProduct(Map<String, String> product, Storage storage, Long vendorCode ) {
        long quantity = Long.parseLong(product.get("quantity"));
        storage.setStock(storage.getStock() + quantity);
        storageRepository.save(storage);

        ListedProduct stockListedProduct = productRepository.findById(vendorCode).get();
        stockListedProduct.setName(product.get("name"));
        stockListedProduct.setPurchasePrice(new BigDecimal(product.get("price")));
        productRepository.save(stockListedProduct);
    }
}
