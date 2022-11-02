package com.demo.spring.warehousemanager.repositories;

import com.demo.spring.warehousemanager.model.ListedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ListedProductRepository extends JpaRepository<ListedProduct, Long> {
    List<ListedProduct> findByName(String name);
}
