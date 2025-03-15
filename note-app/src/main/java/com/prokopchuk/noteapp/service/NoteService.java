package com.prokopchuk.noteapp.service;

import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.commons.dto.NoteFileDto;
import java.util.Optional;

public interface NoteService {

    Optional<NoteDto> getNoteById(Long id);

    Long createNote(NoteDto noteDto);

    Long updateNote(Long id, NoteDto noteDto);

    boolean deleteNoteById(Long id);

    void deleteNotesByUserId(Long userId);

    boolean existsNoteById(Long id);

    Long createNoteFile(NoteFileDto noteFileDto);

    Optional<NoteFileDto> getNoteFileByNoteIdAndFileId(Long noteId, Long fileId);
}
