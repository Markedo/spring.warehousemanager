package com.demo.spring.warehousemanager.repositories;

import com.demo.spring.warehousemanager.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Repository
@Transactional
public interface StorageRepository extends JpaRepository<Storage, Long> {
    Storage findByVendorCodeAndWarehouse(Long vendorCode, String warehouse);

    List<Storage> findByWarehouse(String warehouse);

    void deleteStorageByVendorCode(Long vendorCode);

    @Query(nativeQuery = true, value="SELECT SUM(STOCK), VENDOR_CODE FROM STORAGE GROUP BY VENDOR_CODE")
    List<Map<String, Object>> stocksOnly();

}
