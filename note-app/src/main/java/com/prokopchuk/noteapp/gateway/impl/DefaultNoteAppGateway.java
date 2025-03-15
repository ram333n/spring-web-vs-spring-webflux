package com.prokopchuk.noteapp.gateway.impl;

import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.commons.dto.UserDto;
import com.prokopchuk.noteapp.gateway.NoteAppGateway;
import com.prokopchuk.noteapp.service.NoteService;
import com.prokopchuk.noteapp.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class DefaultNoteAppGateway implements NoteAppGateway {

    private final UserService userService;
    private final NoteService noteService;

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userService.getUserById(id);
    }

    @Override
    @Transactional
    public Long createUser(UserDto userDto) {
        return userService.createUser(userDto);
    }

    @Override
    @Transactional
    public Long updateUser(Long id, UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @Override
    @Transactional
    public boolean deleteUserById(Long userId) {
        if (!userService.existsUserById(userId)) {
            return false;
        }

        noteService.deleteNotesByUserId(userId);
        userService.deleteUserById(userId);

        return true;
    }

    @Override
    public Optional<NoteDto> getNoteById(Long id) {
        return noteService.getNoteById(id);
    }

    @Override
    @Transactional
    public Long createNote(NoteDto noteDto) {
        return noteService.createNote(noteDto);
    }

    @Override
    @Transactional
    public Long updateNote(Long id, NoteDto noteDto) {
        return noteService.updateNote(id, noteDto);
    }

    @Override
    @Transactional
    public boolean deleteNoteById(Long id) {
        return noteService.deleteNoteById(id);
    }
}
