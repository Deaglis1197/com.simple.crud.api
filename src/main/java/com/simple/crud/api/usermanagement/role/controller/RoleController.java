package com.simple.crud.api.usermanagement.role.controller;

import com.simple.crud.api.usermanagement.role.dto.ActionResponse;
import com.simple.crud.api.usermanagement.role.dto.RoleRequest;
import com.simple.crud.api.usermanagement.role.dto.RoleResponse;
import com.simple.crud.api.usermanagement.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/get-all")
    @PreAuthorize("hasAuthority(@auth.READ_ROLE)")
    public ResponseEntity<List<RoleResponse>> getAll() {
        return ResponseEntity.ok(roleService.getAll());
    }

    @GetMapping("/actions")
    @PreAuthorize("hasAuthority(@auth.READ_ROLE)")
    public ResponseEntity<List<ActionResponse>> getAllActions() {
        return ResponseEntity.ok(roleService.getAllActions());
    }

    @GetMapping("/get-by-id/{id}")
    @PreAuthorize("hasAuthority(@auth.READ_ROLE)")
    public ResponseEntity<RoleResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority(@auth.CREATE_ROLE)")
    public ResponseEntity<RoleResponse> create(@RequestBody RoleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roleService.create(request));
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority(@auth.UPDATE_ROLE)")
    public ResponseEntity<RoleResponse> update(@PathVariable Long id, @RequestBody RoleRequest request) {
        return ResponseEntity.ok(roleService.update(id, request));
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority(@auth.DELETE_ROLE)")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
