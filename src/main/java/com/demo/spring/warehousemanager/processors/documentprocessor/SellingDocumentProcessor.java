package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.ListedProduct;
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
        List<Storage> nullifiedStorages = Collections.synchronizedList(new LinkedList<Storage>());
        List<ListedProduct> editedStockListedProducts = Collections.synchronizedList(new LinkedList<ListedProduct>());
        String warehouse = sellingDocument.getWarehouse();
        sellingDocument.getProducts().forEach(
                product -> {
                    Long vendorCode = Long.parseLong(product.get("vendorCode"));
                    Storage storage = storageRepository.findByVendorCodeAndWarehouse(vendorCode, warehouse);
                    if (storage == null)
                        throw new IllegalArgumentException("Can't find product with provided vendor code.");

                    long quantity = Long.parseLong(product.get("quantity"));
                    long editedStocks = storage.getStock() - quantity;
                    if (editedStocks < 0)
                        throw new IllegalArgumentException("Provided quantity exceeding stocks.");
                    else {
                        storage.setStock(editedStocks);
                        editedStorages.add(storage);
                    }
                    if (editedStocks == 0) nullifiedStorages.add(storage);

                    ListedProduct stockListedProduct = productRepository.findById(vendorCode).get();
                    if (stockListedProduct == null)
                        throw new IllegalArgumentException("Can't find product with provided vendor code.");
                    stockListedProduct.setSellingPrice(new BigDecimal(product.get("price")));
                    editedStockListedProducts.add(stockListedProduct);
                }
        );
        storageRepository.saveAll(editedStorages);
        storageRepository.deleteAll(nullifiedStorages);
        productRepository.saveAll(editedStockListedProducts);
    }

}
