package com.prokopchuk.reactivenoteapp.service.impl;

import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.commons.dto.NoteFileDto;
import com.prokopchuk.commons.exception.NotFoundException;
import com.prokopchuk.reactivenoteapp.domain.Note;
import com.prokopchuk.reactivenoteapp.domain.NoteFile;
import com.prokopchuk.reactivenoteapp.repository.NoteFilesRepository;
import com.prokopchuk.reactivenoteapp.repository.NoteRepository;
import com.prokopchuk.reactivenoteapp.service.NoteService;
import com.prokopchuk.reactivenoteapp.service.mapper.NoteMapper;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultNoteService implements NoteService {

    private final NoteRepository noteRepository;
    private final NoteFilesRepository noteFilesRepository;
    private final NoteMapper mapper;

    @Override
    public Mono<NoteDto> getNoteById(Long id) {
        return Mono.zip(
                noteRepository.findById(id)
                    .map(mapper::toDto),
                noteFilesRepository.findAllByNoteId(id)
                    .map(mapper::toDto)
                    .collectList()
            )
            .map(args -> {
                NoteDto result = args.getT1();
                List<NoteFileDto> files = args.getT2();

                result.setAttachedFiles(files);

                return result;
            });
    }

    @Override
    @Transactional
    public Mono<Long> createNote(NoteDto noteDto) {
        Note entity = mapper.toEntity(noteDto);

        return noteRepository.save(entity)
            .map(Note::getId);
    }

    @Override
    @Transactional
    public Mono<Long> updateNote(Long id, NoteDto noteDto) {
        return noteRepository.findById(id)
            .switchIfEmpty(Mono.error(new NotFoundException(String.format("Note with id: %s not found", id))))
            .doOnNext(entityToUpdate -> mapper.map(noteDto, entityToUpdate))
            .flatMap(noteRepository::save)
            .map(Note::getId);
    }

    @Override
    @Transactional
    public Mono<Boolean> deleteNoteById(Long id) {
        return noteRepository.deleteNoteById(id)
            .map(rowsDeleted -> rowsDeleted > 0);
    }

    @Override
    @Transactional
    public Mono<Void> deleteNotesByUserId(Long userId) {
        return noteRepository.deleteNotesByUserId(userId);
    }

    @Override
    public Mono<Boolean> existsNoteById(Long id) {
        return noteRepository.existsById(id);
    }

    @Override
    @Transactional
    public Mono<Long> createNoteFile(NoteFileDto noteFileDto) {
        return validateNoteExistence(noteFileDto.getNoteId())
            .map(validated -> mapper.toEntity(noteFileDto))
            .flatMap(noteFilesRepository::save)
            .map(NoteFile::getId);
    }

    private Mono<Boolean> validateNoteExistence(Long noteId) {
        return existsNoteById(noteId)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new NotFoundException(String.format("Note with id: %s not found", noteId)));
                }

                return Mono.just(true);
            });
    }

    @Override
    public Mono<NoteFileDto> getNoteFileByNoteIdAndFileId(Long noteId, Long fileId) {
        return noteFilesRepository.findById(fileId)
            .filter(file -> Objects.equals(file.getNoteId(), noteId))
            .map(mapper::toDto);
    }

    @Override
    @Transactional
    public Mono<Void> deleteNoteFileByFileId(Long fileId) {
        return noteFilesRepository.deleteById(fileId);
    }

    @Override
    public Flux<NoteFileDto> getNoteFilesByNoteId(Long noteId) {
        return noteFilesRepository.findAllByNoteId(noteId)
            .map(mapper::toDto);
    }
}
