package com.simple.crud.api.config;

import com.simple.crud.api.usermanagement.action.entity.Action;
import com.simple.crud.api.usermanagement.action.enums.ActionType;
import com.simple.crud.api.usermanagement.action.repository.ActionRepository;
import com.simple.crud.api.usermanagement.role.entity.Role;
import com.simple.crud.api.usermanagement.role.repository.RoleRepository;
import com.simple.crud.api.usermanagement.user.entity.User;
import com.simple.crud.api.usermanagement.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final ActionRepository actionRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        Set<Action> allActions = Arrays.stream(ActionType.values())
                .map(type -> actionRepository.findByName(type)
                        .orElseGet(() -> actionRepository.save(Action.builder().name(type).build())))
                .collect(Collectors.toSet());

        Role adminRole = roleRepository.findByName("ADMIN")
                .orElseGet(() -> roleRepository.save(Role.builder().name("ADMIN").actions(allActions).build()));

        if (roleRepository.findByName("USER").isEmpty()) {
            roleRepository.save(Role.builder().name("USER").actions(Set.of()).build());
        }

        if (userRepository.count() == 0) {
            userRepository.save(User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles(Set.of(adminRole))
                    .build());
        }
    }
}
