package com.simple.crud.api.usermanagement.action.repository;

import com.simple.crud.api.usermanagement.action.entity.Action;
import com.simple.crud.api.usermanagement.action.enums.ActionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActionRepository extends JpaRepository<Action, Long> {
    Optional<Action> findByName(ActionType name);
}
