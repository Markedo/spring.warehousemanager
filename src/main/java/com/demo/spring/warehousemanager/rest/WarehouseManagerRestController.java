package com.demo.spring.warehousemanager.rest;

import com.demo.spring.warehousemanager.model.ListedProduct;
import com.demo.spring.warehousemanager.model.Warehouse;
import com.demo.spring.warehousemanager.model.documents.AdmissionDocument;
import com.demo.spring.warehousemanager.model.documents.LoggedDocument;
import com.demo.spring.warehousemanager.model.documents.MovingDocument;
import com.demo.spring.warehousemanager.model.documents.SellingDocument;
import com.demo.spring.warehousemanager.processors.StorageProcessor;
import com.demo.spring.warehousemanager.repositories.LoggedDocumetRepository;
import com.demo.spring.warehousemanager.repositories.ListedProductRepository;
import com.demo.spring.warehousemanager.repositories.StorageRepository;
import com.demo.spring.warehousemanager.processors.documentprocessor.MainDocumentProcessor;
import com.demo.spring.warehousemanager.repositories.WarehouseRepository;
import com.demo.spring.warehousemanager.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class WarehouseManagerRestController {
    private final String SERVICE_PATH = "/api";
    private final String GET_DOC_PATH = SERVICE_PATH + "/getDoc";
    private final String GET_PRODUCT_PATH = SERVICE_PATH + "/getProduct";
    private final String CHECK_STOCK_PATH = SERVICE_PATH + "/getStock";
    private final String POST_ADMISSION_DOC = SERVICE_PATH + "/postAdmissionDoc";
    private final String POST_MOVING_DOC = SERVICE_PATH + "/postMovingDoc";
    private final String POST_NEW_WAREHOUSE = SERVICE_PATH + "/postNewWarehouse";
    private final String POST_SELLING_DOC = SERVICE_PATH + "/postSellingDoc";


    @Autowired
    private MainDocumentProcessor mainDocumentProcessor;

    @Autowired
    private StorageProcessor storageProcessor;

    @Autowired
    private ListedProductRepository listedProductRepository;

    @Autowired
    private LoggedDocumetRepository loggedDocumetRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Value("${page.title}")
    private String PAGE_TITLE;

    private org.slf4j.Logger log = LoggerFactory.getLogger(WarehouseManagerRestController.class);

    @GetMapping(GET_PRODUCT_PATH)
    List<ListedProduct> getProduct(@RequestParam(required = false) String name) {
        log.info("Get all products request received via REST API");
        if (name == null) return listedProductRepository.findAll();
        else return listedProductRepository.findByName(name);
    }

    @GetMapping(CHECK_STOCK_PATH)
    List getAllStocks(@RequestParam(required = false) String warehouse) {
        log.info("Get all stocks request received via REST API");
        if (warehouse == null) return storageProcessor.getAllWarehousesStocks();
        else {
            if(mainDocumentProcessor.validateWarehouse(warehouse)) {
                return storageProcessor.getExactWarehouseStocks(warehouse);
            }
        }
        throw new IllegalArgumentException("Incorrect request.");
    }

    @GetMapping(GET_DOC_PATH)
    LoggedDocument getDocument(@RequestParam String docNumber) {
        log.info("Get document request received via REST API");
        if(docNumber.isEmpty()) throw new IllegalArgumentException("Document number is not provided");
        LoggedDocument document = loggedDocumetRepository.findByNumber(docNumber);
        if (document != null) return document;
        else throw new IllegalArgumentException("Can't find document with provided number.");
    }

    @PostMapping(POST_SELLING_DOC)
    void postSellDoc(@RequestBody String request) {
        log.info("Selling document received via REST API");
        log.debug("JSON request body: " + request);
        try {
            Map<String, Object>parsedJson = JsonUtils.deserializeStr(request);
            SellingDocument document = new SellingDocument();
            document.setDate(LocalDateTime.now());
            document.setNumber((String) parsedJson.get("number"));
            document.setWarehouse((String) parsedJson.get("warehouse"));
            document.setProducts((List<Map<String, String>>) parsedJson.get("products"));
            mainDocumentProcessor.processSellingDoc(document);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    @PostMapping(POST_ADMISSION_DOC)
    void postAdmissionDoc(@RequestBody String request) {
        log.info("Admission document received via REST API");
        log.debug("JSON request body: " + request);
        try {
            Map<String, Object>parsedJson = JsonUtils.deserializeStr(request);
            AdmissionDocument document = new AdmissionDocument();
            document.setDate(LocalDateTime.now());
            document.setNumber((String) parsedJson.get("number"));
            document.setWarehouse((String) parsedJson.get("warehouse"));
            document.setProducts((List<Map<String, String>>) parsedJson.get("products"));
            mainDocumentProcessor.processAdmissionDoc(document);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    @PostMapping(POST_MOVING_DOC)
    void postMovingDoc(@RequestBody String request) {
        log.info("Moving document received via REST API");
        log.debug("JSON request body: " + request);
        try {
            Map<String, Object>parsedJson = JsonUtils.deserializeStr(request);
            MovingDocument document = new MovingDocument();
            document.setDate(LocalDateTime.now());
            document.setNumber((String) parsedJson.get("number"));
            document.setToWarehouse((String) parsedJson.get("toWarehouse"));
            document.setFromWarehouse((String) parsedJson.get("fromWarehouse"));
            document.setProducts((List<Map<String, String>>) parsedJson.get("products"));
            mainDocumentProcessor.processMovingDoc(document);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }

    @PostMapping(POST_NEW_WAREHOUSE)
    void postNewWarehouse(@RequestBody String request) {
        log.info("Post new warehouse request received via REST API");
        log.debug("JSON request body: " + request);
        try {
            Map<String, Object>parsedJson = JsonUtils.deserializeStr(request);
            String newWarehouseCode = parsedJson.get("warehouse").toString();
            if(newWarehouseCode != null) {
                warehouseRepository.save(new Warehouse(newWarehouseCode));
            }
            else throw new IllegalArgumentException("Incorrect warehouse code");
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }
    }

}
