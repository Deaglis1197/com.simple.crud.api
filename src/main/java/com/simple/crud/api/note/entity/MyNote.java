package com.simple.crud.api.note.entity;

import com.simple.crud.api.usermanagement.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "my_notes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MyNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdUser;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "last_modified_date")
    private LocalDateTime lastModifiedDate;

    @Column(name = "note_text", nullable = false, columnDefinition = "TEXT")
    private String noteText;
}
