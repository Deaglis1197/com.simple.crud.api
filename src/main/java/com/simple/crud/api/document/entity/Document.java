package com.simple.crud.api.document.entity;

import com.simple.crud.api.usermanagement.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_user_id", nullable = false)
    private User createdUser;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String filename;

    @Column(name = "file_destination", nullable = false)
    private String fileDestination;
}
