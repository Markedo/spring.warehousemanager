package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.Product;
import com.demo.spring.warehousemanager.model.Storage;
import com.demo.spring.warehousemanager.model.documents.SellingDocument;
import com.demo.spring.warehousemanager.repositories.ProductRepository;
import com.demo.spring.warehousemanager.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Component
class SellingDocumentProcessor {
    @Autowired
    StorageRepository storageRepository;

    @Autowired
    ProductRepository productRepository;

    void processDocument(SellingDocument sellingDocument) throws IllegalArgumentException {
        List<Storage> editedStorages = Collections.synchronizedList(new LinkedList<Storage>());
        List<Product> editedStockProducts = Collections.synchronizedList(new LinkedList<Product>());

        sellingDocument.getProducts().forEach(
                product -> {
                    String warehouse = sellingDocument.getWarehouse();
                    long vendorCode = Long.parseLong(product.get("vendorCode").toString());

                    Storage storage = storageRepository.findByVendorCodeAndWarehouse(vendorCode, warehouse);
                    if (storage == null)
                        throw new IllegalArgumentException("Can't find product with provided vendor code");
                    long quantity = Long.parseLong(product.get("quantity").toString());
                    storage.setStock(storage.getStock() - quantity);
                    editedStorages.add(storage);

                    Product stockProduct = productRepository.findById(vendorCode).get();
                    if (stockProduct == null)
                        throw new IllegalArgumentException("Can't find product with provided vendor code");
                    stockProduct.setSellingPrice(new BigDecimal(product.get("sellingPrice").toString()));
                    editedStockProducts.add(stockProduct);
                }
        );
    }
}
