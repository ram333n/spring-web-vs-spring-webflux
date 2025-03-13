package com.prokopchuk.noteapp.repository;

import com.prokopchuk.noteapp.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {

    long deleteNoteById(Long id);

}
