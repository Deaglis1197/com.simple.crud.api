package com.simple.crud.api.document.service;

import com.simple.crud.api.document.dto.DocumentResponse;
import com.simple.crud.api.document.dto.DocumentSummaryResponse;
import com.simple.crud.api.document.dto.DocumentUploadRequest;
import com.simple.crud.api.document.entity.Document;
import com.simple.crud.api.document.repository.DocumentRepository;
import com.simple.crud.api.usermanagement.user.entity.User;
import com.simple.crud.api.usermanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final UserService userService;

    @Value("${app.documents.upload-dir:uploads/documents}")
    private String uploadDir;

    @Value("${app.documents.max-file-size-mb:50}")
    private long maxFileSizeMb;

    @Value("${app.documents.max-file-count:10}")
    private int maxFileCount;

    @Value("#{'${app.documents.allowed-types:pdf,png,jpg,jpeg,docx,txt}'.split(',')}")
    private List<String> allowedTypes;

    public DocumentResponse upload(DocumentUploadRequest request) {
        User currentUser = userService.getCurrentUser();

        long count = documentRepository.countByCreatedUserId(currentUser.getId());
        if (count >= maxFileCount) {
            throw new IllegalStateException(
                    "Document limit reached. Maximum allowed per user: " + maxFileCount);
        }

        String extension = extractExtension(request.getFilename());
        if (!allowedTypes.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException(
                    "File type not allowed: " + extension + ". Allowed types: " + allowedTypes);
        }

        byte[] fileBytes = Base64.getDecoder().decode(request.getBase64Content());

        long maxBytes = maxFileSizeMb * 1024 * 1024;
        if (fileBytes.length > maxBytes) {
            throw new IllegalArgumentException(
                    "File size exceeds the maximum allowed size of " + maxFileSizeMb + "MB");
        }

        Path userDir = Paths.get(uploadDir, String.valueOf(currentUser.getId()));
        try {
            Files.createDirectories(userDir);
            Path filePath = userDir.resolve(UUID.randomUUID() + "_" + request.getFilename());
            Files.write(filePath, fileBytes);

            Document document = Document.builder()
                    .createdUser(currentUser)
                    .description(request.getDescription())
                    .filename(request.getFilename())
                    .fileDestination(filePath.toString())
                    .build();

            return toDetailResponse(documentRepository.save(document), request.getBase64Content());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file to disk", e);
        }
    }

    public DocumentResponse getById(Long id) {
        Document document = findDocumentOwnedByCurrentUser(id);
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(document.getFileDestination()));
            return toDetailResponse(document, Base64.getEncoder().encodeToString(fileBytes));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file from disk", e);
        }
    }

    public List<DocumentSummaryResponse> getMyDocuments() {
        User currentUser = userService.getCurrentUser();
        return documentRepository.findByCreatedUserId(currentUser.getId())
                .stream().map(this::toSummaryResponse).toList();
    }

    public void delete(Long id) {
        Document document = findDocumentOwnedByCurrentUser(id);
        try {
            Files.deleteIfExists(Paths.get(document.getFileDestination()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file from disk", e);
        }
        documentRepository.delete(document);
    }

    private Document findDocumentOwnedByCurrentUser(Long id) {
        User currentUser = userService.getCurrentUser();
        Document document = documentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Document not found with id: " + id));
        if (!document.getCreatedUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }
        return document;
    }

    private String extractExtension(String filename) {
        int dot = filename.lastIndexOf('.');
        if (dot < 0 || dot == filename.length() - 1) {
            throw new IllegalArgumentException("Filename has no extension: " + filename);
        }
        return filename.substring(dot + 1);
    }

    private DocumentResponse toDetailResponse(Document document, String base64) {
        return new DocumentResponse(
                document.getId(),
                document.getCreatedUser().getId(),
                document.getDescription(),
                document.getFilename(),
                base64);
    }

    private DocumentSummaryResponse toSummaryResponse(Document document) {
        return new DocumentSummaryResponse(document.getId(), document.getFilename());
    }
}
