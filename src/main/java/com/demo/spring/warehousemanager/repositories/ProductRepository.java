package com.demo.spring.warehousemanager.repositories;

import com.demo.spring.warehousemanager.model.ListedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ProductRepository extends JpaRepository<ListedProduct, Long> {
}
