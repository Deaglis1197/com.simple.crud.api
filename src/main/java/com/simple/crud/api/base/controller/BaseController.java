package com.simple.crud.api.base.controller;

import com.simple.crud.api.base.dto.ConsoleDto;
import com.simple.crud.api.base.service.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/base-controller")
@RequiredArgsConstructor
public class BaseController {

    private final BaseService baseService;

    @PostMapping("/write-console")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> writeConsole(@RequestBody ConsoleDto request) {
        baseService.writeConsole(request.getMessage());
        return ResponseEntity.ok(null);
    }
}
