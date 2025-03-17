package com.prokopchuk.reactivenoteapp.repository;


import com.prokopchuk.reactivenoteapp.domain.Note;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface NoteRepository extends ReactiveCrudRepository<Note, Long> {

    Mono<Long> deleteNoteById(Long id);

    Mono<Void> deleteNotesByUserId(Long userId);

}
