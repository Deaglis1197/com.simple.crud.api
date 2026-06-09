package com.simple.crud.api.usermanagement.role.service;

import com.simple.crud.api.usermanagement.action.entity.Action;
import com.simple.crud.api.usermanagement.action.repository.ActionRepository;
import com.simple.crud.api.usermanagement.role.dto.ActionResponse;
import com.simple.crud.api.usermanagement.role.dto.RoleRequest;
import com.simple.crud.api.usermanagement.role.dto.RoleResponse;
import com.simple.crud.api.usermanagement.role.entity.Role;
import com.simple.crud.api.usermanagement.role.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final ActionRepository actionRepository;

    public List<RoleResponse> getAll() {
        return roleRepository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public RoleResponse getById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + id));
        return toResponse(role);
    }

    public RoleResponse create(RoleRequest request) {
        if (roleRepository.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Role already exists with name: " + request.getName());
        }
        Set<Action> actions = resolveActions(request.getActionIds());
        Role role = Role.builder()
                .name(request.getName())
                .actions(actions)
                .build();
        return toResponse(roleRepository.save(role));
    }

    public RoleResponse update(Long id, RoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + id));
        roleRepository.findByName(request.getName())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> { throw new IllegalArgumentException("Role already exists with name: " + request.getName()); });
        role.setName(request.getName());
        role.setActions(resolveActions(request.getActionIds()));
        return toResponse(roleRepository.save(role));
    }

    public List<ActionResponse> getAllActions() {
        return actionRepository.findAll().stream()
                .map(a -> new ActionResponse(a.getId(), a.getName().name()))
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + id));
        if ("ADMIN".equals(role.getName())) {
            throw new IllegalStateException("ADMIN role cannot be deleted");
        }
        roleRepository.delete(role);
    }

    private Set<Action> resolveActions(Set<Long> actionIds) {
        if (actionIds == null || actionIds.isEmpty()) {
            return new HashSet<>();
        }
        return new HashSet<>(actionRepository.findAllById(actionIds));
    }

    private RoleResponse toResponse(Role role) {
        Set<ActionResponse> actionResponses = role.getActions().stream()
                .map(a -> new ActionResponse(a.getId(), a.getName().name()))
                .collect(Collectors.toSet());
        return new RoleResponse(role.getId(), role.getName(), actionResponses);
    }
}
