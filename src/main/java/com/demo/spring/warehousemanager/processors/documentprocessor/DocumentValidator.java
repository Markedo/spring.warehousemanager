package com.demo.spring.warehousemanager.processors.documentprocessor;

import com.demo.spring.warehousemanager.model.documents.*;
import com.demo.spring.warehousemanager.repositories.LoggedDocumetRepository;
import com.demo.spring.warehousemanager.repositories.WarehouseRepository;
import com.demo.spring.warehousemanager.utils.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class DocumentValidator {

    @Autowired
    LoggedDocumetRepository loggedDocumetRepository;

    @Autowired
    WarehouseRepository warehouseRepository;

    public boolean isDocumentValid(BasicDocument document) {
        if(document.getNumber().isEmpty() || !RegexUtil.checkRegex(document.getNumber().toString(), ".+")) {
            throw new IllegalArgumentException("Invalid document number.");
        }
        if(isDocumentNumberExist(document.getNumber())) {
            throw new IllegalArgumentException("Document with provided number already exist in database.");
        }
        if(!isDocTypeValid(document)) {
            throw new IllegalArgumentException("Invalid document type.");
        }

        if(document.getDate() == null) {
            throw new IllegalArgumentException("Document has no date.");
        }

        if(document.getDocType().equals(DocType.ADMISSION)) {
            if(!isWarehouseValid(((AdmissionDocument) document).getWarehouse())) {
                throw new IllegalArgumentException("Invalid warehouse.");
            }
            isProductValid(((AdmissionDocument) document).getProducts(), DocType.ADMISSION);
        }

        if (document.getDocType().equals(DocType.SELL)) {
            if(!isWarehouseValid(((SellingDocument) document).getWarehouse())) {
                throw new IllegalArgumentException("Invalid warehouse.");
            }
            isProductValid(((SellingDocument) document).getProducts(), DocType.SELL);
        }

        if (document.getDocType().equals(DocType.MOVE)) {
            if(!isWarehouseValid(((MovingDocument) document).getFromWarehouse())
                    || !isWarehouseValid(((MovingDocument) document).getToWarehouse()) ) {
                throw new IllegalArgumentException("Invalid warehouse.");
            }
        }
        return true;
    }

    private boolean isProductValid(List<Map<String, String>> products, DocType docType) {
        boolean valid = false;
        products.forEach(
                product -> {
                    if (!product.containsKey("vendorCode") || product.get("vendorCode").isEmpty() || Long.parseLong(product.get("vendorCode")) < 0) {
                        throw new IllegalArgumentException("Product contains incorrect \"vendorCode\" section");
                    }
                    if (!product.containsKey("quantity") || product.get("quantity").isEmpty() || Long.parseLong(product.get("quantity")) < 0) {
                        throw new IllegalArgumentException("Product contains incorrect \"quantity\" section");
                    }

                    if (docType.equals(DocType.ADMISSION) &&
                            (!product.containsKey("name") || product.get("name").isEmpty())) {
                        throw new IllegalArgumentException("Product contains incorrect \"name\" section");
                    }

                    if ((docType.equals(DocType.ADMISSION) || docType.equals(DocType.SELL)) &&
                            (!product.containsKey("price") || product.get("price").isEmpty())
                            || new BigDecimal(product.get("price")).compareTo(BigDecimal.ZERO) < 0) {
                        throw new IllegalArgumentException("Product contains incorrect \"price\" section");
                    }
                }
        );
        valid = true;
        return valid;
    }

    private boolean isDocTypeValid(BasicDocument document) {
       boolean checker = Arrays.stream(DocType.values()).anyMatch((t) -> t.name().equals(document.getDocType().name()));
       return checker;
    }

    private boolean isDocumentNumberExist(String documentNumber) {
        return loggedDocumetRepository.findByNumber(documentNumber) != null;
    }

    private boolean isWarehouseValid(String warehouse) {
        return warehouseRepository.findByWarehouseCode(warehouse) != null;
    }
}
