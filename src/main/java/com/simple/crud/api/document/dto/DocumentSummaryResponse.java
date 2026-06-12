package com.simple.crud.api.document.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentSummaryResponse {

    private Long id;
    private String filename;
}
