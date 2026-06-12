package com.simple.crud.api.note.repository;

import com.simple.crud.api.note.entity.MyNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyNoteRepository extends JpaRepository<MyNote, Long> {

    List<MyNote> findByCreatedUserId(Long userId);

    long countByCreatedUserId(Long userId);
}
