package com.prokopchuk.noteapp.service;

import com.prokopchuk.commons.dto.NoteDto;
import java.util.Optional;

public interface NoteService {

    Optional<NoteDto> getNoteById(Long id);

    Long createNote(NoteDto noteDto);

    Long updateNote(Long id, NoteDto noteDto);

    boolean deleteNoteById(Long id);

}
