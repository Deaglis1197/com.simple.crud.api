package com.simple.crud.api.document.repository;

import com.simple.crud.api.document.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentRepository extends JpaRepository<Document, Long> {

    List<Document> findByCreatedUserId(Long userId);

    long countByCreatedUserId(Long userId);
}
