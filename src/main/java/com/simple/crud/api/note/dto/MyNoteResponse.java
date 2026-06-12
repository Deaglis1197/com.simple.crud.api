package com.simple.crud.api.note.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyNoteResponse {

    private Long id;
    private Long createdUserId;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private String noteText;
}
