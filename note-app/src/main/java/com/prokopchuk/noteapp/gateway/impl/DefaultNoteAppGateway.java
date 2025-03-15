package com.prokopchuk.noteapp.gateway.impl;

import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.commons.dto.NoteFileDto;
import com.prokopchuk.commons.dto.UserDto;
import com.prokopchuk.commons.exception.NotFoundException;
import com.prokopchuk.noteapp.gateway.NoteAppGateway;
import com.prokopchuk.noteapp.service.FileService;
import com.prokopchuk.noteapp.service.NoteService;
import com.prokopchuk.noteapp.service.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class DefaultNoteAppGateway implements NoteAppGateway {

    private final UserService userService;
    private final NoteService noteService;
    private final FileService fileService;

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

    @Override
    public Long uploadNoteFile(Long noteId, MultipartFile file) {
        if (!noteService.existsNoteById(noteId)) {
            throw new NotFoundException(String.format("Note with id: %s does not exist", noteId));
        }

        String fileName = file.getOriginalFilename();
        String prefix = FilenameUtils.getBaseName(fileName);
        String extension = FilenameUtils.getExtension(fileName);

        String importCode = fileService.uploadFile(file);

        return noteService.createNoteFile(createNoteFileDto(noteId, importCode, prefix, extension));
    }

    private NoteFileDto createNoteFileDto(Long noteId, String importCode, String filePrefix, String fileExtension) {
        NoteFileDto dto = new NoteFileDto();
        dto.setNoteId(noteId);
        dto.setImportCode(importCode);
        dto.setFilePrefix(filePrefix);
        dto.setFileExtension(fileExtension);

        return dto;
    }

    @Override
    public Optional<NoteFileDto> getNoteFileByNoteIdAndFileId(Long noteId, Long fileId) {
        return noteService.getNoteFileByNoteIdAndFileId(noteId, fileId);
    }

}
