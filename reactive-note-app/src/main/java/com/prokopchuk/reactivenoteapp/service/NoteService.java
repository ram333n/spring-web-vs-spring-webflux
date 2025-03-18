package com.prokopchuk.reactivenoteapp.service;

import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.commons.dto.NoteFileDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NoteService {

    Mono<NoteDto> getNoteById(Long id);

    Mono<Long> createNote(NoteDto noteDto);

    Mono<Long> updateNote(Long id, NoteDto noteDto);

    Mono<Boolean> deleteNoteById(Long id);

    Mono<Void> deleteNotesByUserId(Long userId);

    Mono<Boolean> existsNoteById(Long id);

    Mono<Long> createNoteFile(NoteFileDto noteFileDto);

    Mono<NoteFileDto> getNoteFileByNoteIdAndFileId(Long noteId, Long fileId);

    Mono<Void> deleteNoteFileByFileId(Long fileId);

    Flux<NoteFileDto> getNoteFilesByNoteId(Long noteId);

}
