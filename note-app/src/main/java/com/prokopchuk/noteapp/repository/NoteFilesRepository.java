package com.prokopchuk.noteapp.repository;

import com.prokopchuk.noteapp.domain.NoteFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteFilesRepository extends JpaRepository<NoteFile, Long> {

}
