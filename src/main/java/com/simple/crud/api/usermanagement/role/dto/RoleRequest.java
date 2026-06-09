package com.simple.crud.api.usermanagement.role.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RoleRequest {
    private String name;
    private Set<Long> actionIds;
}
