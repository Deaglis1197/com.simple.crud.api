package com.simple.crud.api.usermanagement.action.entity;

import com.simple.crud.api.usermanagement.action.enums.ActionType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "actions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private ActionType name;
}
