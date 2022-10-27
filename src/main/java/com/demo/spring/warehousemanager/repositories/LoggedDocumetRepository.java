package com.demo.spring.warehousemanager.repositories;
import com.demo.spring.warehousemanager.model.documents.LoggedDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface LoggedDocumetRepository extends JpaRepository<LoggedDocument, Long> {
}
