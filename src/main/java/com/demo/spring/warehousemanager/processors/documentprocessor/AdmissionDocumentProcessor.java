package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.ListedProduct;
import com.demo.spring.warehousemanager.model.Storage;
import com.demo.spring.warehousemanager.model.documents.AdmissionDocument;
import com.demo.spring.warehousemanager.repositories.ListedProductRepository;
import com.demo.spring.warehousemanager.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

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
        HashSet<Long> newVendorCodes = new HashSet();
        String warehouse = admissionDocument.getWarehouse();
        admissionDocument.getProducts().forEach(
                product -> {
                    //Product with zero quantity would not be added
                    if (Long.parseLong(product.get("quantity")) >= 0) {
                        Long vendorCode = Long.parseLong(product.get("vendorCode"));
                        ListedProduct listedProduct = listedProductRepository.findByVendorCode(vendorCode);
                        if (listedProduct == null) {
                            if(!newVendorCodes.contains(vendorCode)) {
                                addNewProduct(product, warehouse, vendorCode, editedStorages, editedProducts);
                                newVendorCodes.add(vendorCode);
                            }
                            else {
                                addNewItemWithSameVendorCode(product, vendorCode, editedStorages, editedProducts);
                            }
                        } else {
                            editExistingProduct(product, warehouse, vendorCode, editedStorages, editedProducts);
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

    private void editExistingProduct(Map<String, String> product, String warehouse, Long vendorCode, List<Storage> editedStorages, List<ListedProduct> editedProducts) {
        Storage storage = storageRepository.findByVendorCodeAndWarehouse(vendorCode, warehouse);
        long quantity = Long.parseLong(product.get("quantity"));
        storage.setStock(storage.getStock() + quantity);
        editedStorages.add(storage);

        ListedProduct stockListedProduct = listedProductRepository.findById(vendorCode).get();
        stockListedProduct.setName(product.get("name"));
        stockListedProduct.setPurchasePrice(new BigDecimal(product.get("price")));
        editedProducts.add(stockListedProduct);
    }

    private void addNewItemWithSameVendorCode(Map<String, String> product, Long vendorCode, List<Storage> editedStorages, List<ListedProduct> editedProducts) {
        ListedProduct addedProduct = editedProducts.stream()
                .filter(p -> vendorCode.equals(p.getVendorCode()))
                .findAny()
                .orElse(null);
        Storage addedStorage = editedStorages.stream()
                .filter(s -> vendorCode.equals(s.getVendorCode()))
                .findAny()
                .orElse(null);
        if(addedProduct!=null && addedStorage != null) {
            editedProducts.remove(addedProduct);
            editedStorages.remove(addedStorage);

            addedProduct.setName(product.get("name"));
            addedProduct.setPurchasePrice(new BigDecimal(product.get("price")));
            editedProducts.add(addedProduct);

            addedStorage.setStock(addedStorage.getStock() +  Long.parseLong(product.get("quantity")));
            editedStorages.add(addedStorage);
        }
    }
}
