package com.demo.spring.warehousemanager.processors;

import com.demo.spring.warehousemanager.model.ListedProduct;
import com.demo.spring.warehousemanager.model.Storage;
import com.demo.spring.warehousemanager.repositories.ListedProductRepository;
import com.demo.spring.warehousemanager.repositories.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.util.*;

@Component
public class StorageProcessor {
    @Autowired
    StorageRepository storageRepository;

    @Autowired
    ListedProductRepository listedProductRepository;

    public List<Map<String, String>> getAllWarehousesStocks() throws IllegalArgumentException {
        List<Map<String, String>> allWarehousesStocks = new LinkedList<>();
        List<Map<String, Object>> stocksDbInfo = storageRepository.stocksOnly();
        if (stocksDbInfo == null) throw new IllegalArgumentException("Can't find any product in database.");

        stocksDbInfo.forEach(
                stock -> {
                    //Remove storage entity if its quantity is zero
                    if(new BigInteger(stock.get("SUM(STOCK)").toString()).compareTo(BigInteger.ZERO) > 0 ) {
                        Long vendorCode = Long.parseLong(stock.get("VENDOR_CODE").toString());
                        Optional<ListedProduct> product = listedProductRepository.findById(vendorCode);
                        String productName = product.get().getName();
                        Map<String, String> infoStock = new HashMap<>();
                        infoStock.put("quantity", stock.get("SUM(STOCK)").toString());
                        infoStock.put("name", productName);
                        infoStock.put("vendorCode", stock.get("VENDOR_CODE").toString());
                        allWarehousesStocks.add(infoStock);
                    }
                    else {
                        storageRepository.deleteStorageByVendorCode(Long.parseLong(stock.get("VENDOR_CODE").toString()));
                    }

                }
        );
        return allWarehousesStocks;
    }
    public List<Map<String, String>> getExactWarehouseStocks(String warehouse) throws IllegalArgumentException {
        List<Storage> stocksDbInfo = storageRepository.findByWarehouse(warehouse);
        List<Map<String, String>> exactWarehouseStocks = new LinkedList<>();
        stocksDbInfo.forEach(
                storage -> {
                    //Remove storage entity if its quantity is zero
                    if(storage.getStock() > 0) {
                        Optional<ListedProduct> product = listedProductRepository.findById(storage.getVendorCode());
                        String productName = product.get().getName();
                        Map<String, String> infoStock = new HashMap<>();
                        infoStock.put("vendorCode", storage.getVendorCode().toString());
                        infoStock.put("name", productName);
                        infoStock.put("quantity", String.valueOf(storage.getStock()));
                        exactWarehouseStocks.add(infoStock);
                    }
                    else {
                        storageRepository.deleteStorageByVendorCode(storage.getVendorCode());
                    }
                }
        );
        return exactWarehouseStocks;
    }
}
