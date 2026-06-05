package com.simple.crud.api.base.controller;
import lombok.RequiredArgsConstructor;

import org.apache.catalina.connector.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.simple.crud.api.base.service.BaseService;
import com.simple.crud.api.base.dto.ConsoleDto;

@RestController
@RequestMapping("/base-controller")
@RequiredArgsConstructor
public class BaseController {

    private final BaseService baseService;

    @PostMapping("/write-console")
    public ResponseEntity<Response> writeConsole(@RequestBody ConsoleDto request) {
        baseService.writeConsole(request.getMessage());

        return ResponseEntity.ok(null);
    }



}
