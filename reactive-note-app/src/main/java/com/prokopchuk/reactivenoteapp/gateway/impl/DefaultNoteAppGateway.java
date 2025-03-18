package com.prokopchuk.reactivenoteapp.gateway.impl;

import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.commons.dto.NoteFileDto;
import com.prokopchuk.commons.dto.UserDto;
import com.prokopchuk.commons.exception.NotFoundException;
import com.prokopchuk.reactivenoteapp.gateway.NoteAppGateway;
import com.prokopchuk.reactivenoteapp.service.NoteService;
import com.prokopchuk.reactivenoteapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultNoteAppGateway implements NoteAppGateway {

    private final NoteService noteService;
    private final UserService userService;

    @Override
    public Mono<UserDto> getUserById(Long id) {
        return userService.getUserById(id);
    }

    @Override
    @Transactional
    public Mono<Long> createUser(UserDto userDto) {
        return userService.createUser(userDto);
    }

    @Override
    @Transactional
    public Mono<Long> updateUser(Long id, UserDto userDto) {
        return userService.updateUser(id, userDto);
    }

    @Override
    @Transactional
    public Mono<Boolean> deleteUserById(Long userId) {
        return userService.existsUserById(userId)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.just(false);
                }

                return noteService.deleteNotesByUserId(userId)
                    .then(userService.deleteUserById(userId))
                    .thenReturn(true);
            });
    }

    @Override
    public Mono<NoteDto> getNoteById(Long id) {
        return noteService.getNoteById(id);
    }

    @Override
    @Transactional
    public Mono<Long> createNote(NoteDto noteDto) {
        return noteService.createNote(noteDto);
    }

    @Override
    @Transactional
    public Mono<Long> updateNote(Long id, NoteDto noteDto) {
        return noteService.updateNote(id, noteDto);
    }

    @Override
    @Transactional
    public Mono<Boolean> deleteNoteById(Long id) {
        return deleteNoteFilesOnFileService(id)
            .then(noteService.deleteNoteById(id));
    }

    private Mono<Void> deleteNoteFilesOnFileService(Long noteId) {
        return Mono.empty(); //TODO: impl
    }

    @Override
    @Transactional
    public Mono<Long> uploadNoteFile(Long noteId, MultipartFile file) {
        return Mono.empty(); //TODO: impl
    }

    @Override
    public Mono<NoteFileDto> getNoteFileByNoteIdAndFileId(Long noteId, Long fileId) {
        return noteService.getNoteFileByNoteIdAndFileId(noteId, fileId);
    }

    @Override
    @Transactional
    public Mono<Void> deleteNoteFileByNoteIdAndFileId(Long noteId, Long fileId) {
        return noteService.getNoteFileByNoteIdAndFileId(noteId, fileId)
            .switchIfEmpty(Mono.error(new NotFoundException(String.format("Note with id: %s does not have file with id: %s", noteId, fileId))))
            .flatMap(noteFile -> {
                return noteService.deleteNoteFileByFileId(fileId); //TODO: impl deleting file by import code
            });
    }
}
