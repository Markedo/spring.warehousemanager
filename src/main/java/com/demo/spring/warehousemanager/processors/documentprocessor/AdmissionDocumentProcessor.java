package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.ListedProduct;
import com.demo.spring.warehousemanager.model.Storage;
import com.demo.spring.warehousemanager.model.documents.AdmissionDocument;
import com.demo.spring.warehousemanager.repositories.ListedProductRepository;
import com.demo.spring.warehousemanager.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Component
class AdmissionDocumentProcessor {
    @Autowired
    StorageRepository storageRepository;

    @Autowired
    ListedProductRepository listedProductRepository;

    void processDocument(AdmissionDocument admissionDocument) throws IllegalArgumentException {
        //Generate synced collections of Storage and ListedProducts to keep an order of entity processing and adding to the database
        List<Storage> editedStorages = Collections.synchronizedList(new LinkedList<Storage>());
        List<ListedProduct> editedProducts = Collections.synchronizedList(new LinkedList<ListedProduct>());
        String warehouse = admissionDocument.getWarehouse();
        admissionDocument.getProducts().forEach(
                product -> {
                    //Product with zero quantity would not be added
                    if (Long.parseLong(product.get("quantity")) > 0) {
                        Long vendorCode = Long.parseLong(product.get("vendorCode"));
                        ListedProduct stockListedProduct = new ListedProduct();
                        try {
                            stockListedProduct = listedProductRepository.findById(vendorCode).get();
                        } catch (Exception e) {
                        }
                        if (stockListedProduct.getVendorCode() == null) {
                            addNewProduct(product, warehouse, vendorCode, editedStorages, editedProducts);
                        } else {
                            Storage storage = storageRepository.findByVendorCodeAndWarehouse(vendorCode, warehouse);
                            if (storage == null) addNewProduct(product, warehouse, vendorCode, editedStorages, editedProducts);
                            else editExistingProduct(product, storage, vendorCode, editedStorages, editedProducts);
                        }
                    }
                }
        );
        storageRepository.saveAll(editedStorages);
        listedProductRepository.saveAll(editedProducts);
    }

    private void addNewProduct(Map<String, String> product, String warehouse, Long vendorCode, List<Storage> editedStorages, List<ListedProduct> editedProducts) {
        ListedProduct newListedProduct = new ListedProduct();
        newListedProduct.setVendorCode(vendorCode);
        newListedProduct.setName(product.get("name"));
        newListedProduct.setPurchasePrice(new BigDecimal(product.get("price")));
        newListedProduct.setSellingPrice(null);
        editedProducts.add(newListedProduct);

        Storage storage = new Storage();
        storage.setWarehouse(warehouse);
        storage.setVendorCode(vendorCode);
        long quantity = Long.parseLong(product.get("quantity"));
        storage.setStock(quantity);
        editedStorages.add(storage);
    };

    private void editExistingProduct(Map<String, String> product, Storage storage, Long vendorCode, List<Storage> editedStorages, List<ListedProduct> editedProducts) {
        long quantity = Long.parseLong(product.get("quantity"));
        storage.setStock(storage.getStock() + quantity);
        editedStorages.add(storage);

        ListedProduct stockListedProduct = listedProductRepository.findById(vendorCode).get();
        stockListedProduct.setName(product.get("name"));
        stockListedProduct.setPurchasePrice(new BigDecimal(product.get("price")));
        editedProducts.add(stockListedProduct);
    }
}
