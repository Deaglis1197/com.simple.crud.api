package com.simple.crud.api.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentResponse {

    private Long id;
    private Long createdUserId;
    private String description;
    private String filename;
    private String base64Content;
}
