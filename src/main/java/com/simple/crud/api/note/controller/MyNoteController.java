package com.simple.crud.api.note.controller;

import com.simple.crud.api.note.dto.MyNoteRequest;
import com.simple.crud.api.note.dto.MyNoteResponse;
import com.simple.crud.api.note.service.MyNoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/my-notes")
@RequiredArgsConstructor
public class MyNoteController {

    private final MyNoteService myNoteService;

    @GetMapping
    @PreAuthorize("hasAuthority(@auth.READ_NOTE)")
    public ResponseEntity<List<MyNoteResponse>> getMyNotes() {
        return ResponseEntity.ok(myNoteService.getMyNotes());
    }

    @GetMapping("/get-my-all-notes")
    @PreAuthorize("hasAuthority(@auth.READ_NOTE)")
    public ResponseEntity<List<MyNoteResponse>> getMyAllNotes() {
        return ResponseEntity.ok(myNoteService.getMyNotes());
    }

    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("hasAuthority(@auth.READ_NOTE)")
    public ResponseEntity<MyNoteResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(myNoteService.getById(id));
    }

    @PostMapping("/create")
    @PreAuthorize("hasAuthority(@auth.CREATE_NOTE)")
    public ResponseEntity<MyNoteResponse> create(@RequestBody MyNoteRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(myNoteService.create(request));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority(@auth.UPDATE_NOTE)")
    public ResponseEntity<MyNoteResponse> update(@PathVariable Long id, @RequestBody MyNoteRequest request) {
        return ResponseEntity.ok(myNoteService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority(@auth.DELETE_NOTE)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        myNoteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
