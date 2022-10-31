package com.demo.spring.warehousemanager.rest;

import com.demo.spring.warehousemanager.model.ListedProduct;
import com.demo.spring.warehousemanager.model.Storage;
import com.demo.spring.warehousemanager.model.documents.AdmissionDocument;
import com.demo.spring.warehousemanager.model.documents.MovingDocument;
import com.demo.spring.warehousemanager.model.documents.SellingDocument;
import com.demo.spring.warehousemanager.repositories.ProductRepository;
import com.demo.spring.warehousemanager.repositories.StorageRepository;
import com.demo.spring.warehousemanager.processors.documentprocessor.MainDocumentProcessor;
import com.demo.spring.warehousemanager.utils.JsonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class WarehouseManagerRestController {
    private final String SERVICE_PATH = "/api";
    private final String GET_ALL_PRODUCTS_PATH = SERVICE_PATH + "/getAllProducts";
    private final String CHECK_STOCK_PATH = SERVICE_PATH + "/getStock";
    private final String SELL_DOC_PATH= SERVICE_PATH + "/postSellDoc";
    private final String ADMISSION_DOC_PATH= SERVICE_PATH + "/postAdmissionDoc";
    private final String MOVING_DOC_PATH= SERVICE_PATH + "/postMovingDoc";
    private final String GET_DOC_PATH = SERVICE_PATH + "/getDoc";

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private MainDocumentProcessor mainDocumentProcessor;

    @Value("${page.title}")
    private String PAGE_TITLE;

    private org.slf4j.Logger log = LoggerFactory.getLogger(WarehouseManagerRestController.class);

    @GetMapping(GET_ALL_PRODUCTS_PATH)
    List<ListedProduct> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping(CHECK_STOCK_PATH)
    List<Storage> getAllStocks() {
        return storageRepository.findAll();
    }

    @PostMapping(SELL_DOC_PATH)
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

    @PostMapping(ADMISSION_DOC_PATH)
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

    @PostMapping(MOVING_DOC_PATH)
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

}
