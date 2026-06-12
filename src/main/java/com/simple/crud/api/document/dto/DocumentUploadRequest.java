package com.simple.crud.api.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentUploadRequest {

    private String filename;
    private String description;
    private String base64Content;
}
