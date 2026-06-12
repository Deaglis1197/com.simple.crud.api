package com.simple.crud.api.note.service;

import com.simple.crud.api.note.dto.MyNoteRequest;
import com.simple.crud.api.note.dto.MyNoteResponse;
import com.simple.crud.api.note.entity.MyNote;
import com.simple.crud.api.note.repository.MyNoteRepository;
import com.simple.crud.api.usermanagement.user.entity.User;
import com.simple.crud.api.usermanagement.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MyNoteService {

    private final MyNoteRepository myNoteRepository;
    private final UserService userService;

    @Value("${app.notes.max-per-user:10}")
    private int maxNotesPerUser;

    public List<MyNoteResponse> getMyNotes() {
        User currentUser = userService.getCurrentUser();
        return myNoteRepository.findByCreatedUserId(currentUser.getId())
                .stream().map(this::toResponse).toList();
    }

    public MyNoteResponse getById(Long id) {
        return toResponse(findNoteOwnedByCurrentUser(id));
    }

    public MyNoteResponse create(MyNoteRequest request) {
        User currentUser = userService.getCurrentUser();
        long count = myNoteRepository.countByCreatedUserId(currentUser.getId());
        if (count >= maxNotesPerUser) {
            throw new IllegalStateException("Note limit reached. Maximum allowed per user: " + maxNotesPerUser);
        }
        MyNote note = MyNote.builder()
                .createdUser(currentUser)
                .createdDate(LocalDateTime.now())
                .noteText(request.getNoteText())
                .build();
        return toResponse(myNoteRepository.save(note));
    }

    public MyNoteResponse update(Long id, MyNoteRequest request) {
        MyNote note = findNoteOwnedByCurrentUser(id);
        note.setNoteText(request.getNoteText());
        note.setLastModifiedDate(LocalDateTime.now());
        return toResponse(myNoteRepository.save(note));
    }

    public void delete(Long id) {
        myNoteRepository.delete(findNoteOwnedByCurrentUser(id));
    }

    private MyNote findNoteOwnedByCurrentUser(Long id) {
        User currentUser = userService.getCurrentUser();
        MyNote note = myNoteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note not found with id: " + id));
        if (!note.getCreatedUser().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("Access denied");
        }
        return note;
    }

    private MyNoteResponse toResponse(MyNote note) {
        return new MyNoteResponse(
                note.getId(),
                note.getCreatedUser().getId(),
                note.getCreatedDate(),
                note.getLastModifiedDate(),
                note.getNoteText()
        );
    }
}
