package com.prokopchuk.noteapp.service.impl;

import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.commons.dto.NoteFileDto;
import com.prokopchuk.commons.exception.NotFoundException;
import com.prokopchuk.noteapp.domain.Note;
import com.prokopchuk.noteapp.domain.NoteFile;
import com.prokopchuk.noteapp.repository.NoteFilesRepository;
import com.prokopchuk.noteapp.repository.NoteRepository;
import com.prokopchuk.noteapp.service.NoteService;
import com.prokopchuk.noteapp.service.mapper.NoteMapper;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultNoteService implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteFilesRepository noteFilesRepository;
    private final NoteMapper mapper;

    @Override
    public Optional<NoteDto> getNoteById(Long id) {
        return noteRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
    public Long createNote(NoteDto noteDto) {
        Note entity = mapper.toEntity(noteDto);

        return noteRepository.save(entity).getId();
    }

    @Override
    public Long updateNote(Long id, NoteDto noteDto) {
        Note toUpdate = noteRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(String.format("Note with id: %s not found", id)));

        mapper.map(noteDto, toUpdate);

        return noteRepository.save(toUpdate).getId();
    }

    @Override
    @Transactional
    public boolean deleteNoteById(Long id) {
        return noteRepository.deleteNoteById(id) > 0L;
    }

    @Override
    @Transactional
    public void deleteNotesByUserId(Long userId) {
        noteRepository.deleteNotesByUserId(userId);
    }

    @Override
    public boolean existsNoteById(Long id) {
        return noteRepository.existsById(id);
    }

    @Override
    public Long createNoteFile(NoteFileDto noteFileDto) {
        if (!existsNoteById(noteFileDto.getNoteId())) {
            throw new NotFoundException(String.format("Note with id: %s not found", noteFileDto.getNoteId()));
        }

        NoteFile entity = mapper.toEntity(noteFileDto);

        return noteFilesRepository.save(entity).getId();
    }

    @Override
    public Optional<NoteFileDto> getNoteFileByNoteIdAndFileId(Long noteId, Long fileId) {
        return noteFilesRepository.findById(fileId)
            .filter(file -> Objects.equals(file.getNoteId(), noteId))
            .map(mapper::toDto);
    }

}
