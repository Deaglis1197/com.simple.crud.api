package com.simple.crud.api.usermanagement.user.controller;

import com.simple.crud.api.usermanagement.user.dto.UserDetailRequest;
import com.simple.crud.api.usermanagement.user.dto.UserDetailResponse;
import com.simple.crud.api.usermanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}/user-detail")
    public ResponseEntity<UserDetailResponse> getUserDetail(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getUserDetail(userId));
    }

    @PostMapping("/{userId}/user-detail")
    public ResponseEntity<UserDetailResponse> createUserDetail(
            @PathVariable Long userId,
            @RequestBody UserDetailRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUserDetail(userId, request));
    }

    @PutMapping("/{userId}/user-detail")
    public ResponseEntity<UserDetailResponse> updateUserDetail(
            @PathVariable Long userId,
            @RequestBody UserDetailRequest request) {
        return ResponseEntity.ok(userService.updateUserDetail(userId, request));
    }
}
