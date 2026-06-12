package com.simple.crud.api.document.controller;

import com.simple.crud.api.document.dto.DocumentResponse;
import com.simple.crud.api.document.dto.DocumentSummaryResponse;
import com.simple.crud.api.document.dto.DocumentUploadRequest;
import com.simple.crud.api.document.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @GetMapping("/get-my-all-documents")
    @PreAuthorize("hasAuthority(@auth.READ_DOCUMENT)")
    public ResponseEntity<List<DocumentSummaryResponse>> getMyDocuments() {
        return ResponseEntity.ok(documentService.getMyDocuments());
    }

    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("hasAuthority(@auth.READ_DOCUMENT)")
    public ResponseEntity<DocumentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getById(id));
    }

    @PostMapping("/upload")
    @PreAuthorize("hasAuthority(@auth.CREATE_DOCUMENT)")
    public ResponseEntity<DocumentResponse> upload(@RequestBody DocumentUploadRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(documentService.upload(request));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority(@auth.DELETE_DOCUMENT)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        documentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
