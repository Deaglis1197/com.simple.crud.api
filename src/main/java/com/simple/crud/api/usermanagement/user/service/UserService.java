package com.simple.crud.api.usermanagement.user.service;

import com.simple.crud.api.usermanagement.action.enums.ActionType;
import com.simple.crud.api.usermanagement.user.dto.UserDetailRequest;
import com.simple.crud.api.usermanagement.user.dto.UserDetailResponse;
import com.simple.crud.api.usermanagement.user.entity.User;
import com.simple.crud.api.usermanagement.user.entity.UserDetail;
import com.simple.crud.api.usermanagement.user.repository.UserDetailRepository;
import com.simple.crud.api.usermanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailRepository userDetailRepository;

    public UserDetailResponse getUserDetail(Long userId) {
        requireSelfOrAction(userId, ActionType.READ_USER_INFO);
        UserDetail detail = userDetailRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User detail not found for user id: " + userId));
        return toResponse(detail);
    }

    public UserDetailResponse createUserDetail(Long userId, UserDetailRequest request) {
        requireSelfOrAction(userId, ActionType.USER_INFO_EDIT);
        if (userDetailRepository.findByUserId(userId).isPresent()) {
            throw new IllegalArgumentException("User detail already exists for user id: " + userId);
        }
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        UserDetail detail = UserDetail.builder()
                .user(user)
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .emailAddress(request.getEmailAddress())
                .gender(request.getGender())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .additionalInfo(request.getAdditionalInfo())
                .build();
        return toResponse(userDetailRepository.save(detail));
    }

    public UserDetailResponse updateUserDetail(Long userId, UserDetailRequest request) {
        requireSelfOrAction(userId, ActionType.USER_INFO_EDIT);
        UserDetail detail = userDetailRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("User detail not found for user id: " + userId));
        detail.setFirstName(request.getFirstName());
        detail.setLastName(request.getLastName());
        detail.setEmailAddress(request.getEmailAddress());
        detail.setGender(request.getGender());
        detail.setPhoneNumber(request.getPhoneNumber());
        detail.setAddress(request.getAddress());
        detail.setAdditionalInfo(request.getAdditionalInfo());
        return toResponse(userDetailRepository.save(detail));
    }

    private void requireSelfOrAction(Long userId, ActionType requiredAction) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Authenticated user not found"));
        boolean isSelf = currentUser.getId().equals(userId);
        boolean hasAction = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(requiredAction.name()));
        if (!isSelf && !hasAction) {
            throw new AccessDeniedException("Access denied");
        }
    }

    private UserDetailResponse toResponse(UserDetail detail) {
        return new UserDetailResponse(
                detail.getId(),
                detail.getUser().getId(),
                detail.getFirstName(),
                detail.getLastName(),
                detail.getEmailAddress(),
                detail.getGender(),
                detail.getPhoneNumber(),
                detail.getAddress(),
                detail.getAdditionalInfo()
        );
    }
}
