package com.prokopchuk.noteapp.repository;

import com.prokopchuk.noteapp.domain.NoteFiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteFilesRepository extends JpaRepository<NoteFiles, Long> {

}
