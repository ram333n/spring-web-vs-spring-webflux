package com.prokopchuk.noteapp.service.impl;

import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.noteapp.repository.NoteRepository;
import com.prokopchuk.noteapp.service.NoteService;
import com.prokopchuk.noteapp.service.mapper.NoteMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultNoteService implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper mapper;

    @Override
    public Optional<NoteDto> getNoteById(Long id) {
        return Optional.empty();
    }

    @Override
    public Long createNote(NoteDto noteDto) {
        return 0L;
    }

    @Override
    public Long updateNote(Long id, NoteDto noteDto) {
        return 0L;
    }

    @Override
    public boolean deleteNoteById(Long id) {
        return false;
    }

}
