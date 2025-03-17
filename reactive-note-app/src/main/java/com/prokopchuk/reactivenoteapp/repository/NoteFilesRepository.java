package com.prokopchuk.reactivenoteapp.repository;

import com.prokopchuk.reactivenoteapp.domain.NoteFile;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface NoteFilesRepository extends ReactiveCrudRepository<NoteFile, Long> {

    Flux<NoteFile> findAllByNoteId(Long noteId);

    Mono<Void> deleteAllByNoteId(Long noteId);

}
