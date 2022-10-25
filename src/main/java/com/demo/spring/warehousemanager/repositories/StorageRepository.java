package com.demo.spring.warehousemanager.repositories;

import com.demo.spring.warehousemanager.model.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface StorageRepository extends JpaRepository<Storage, Long> {
}
