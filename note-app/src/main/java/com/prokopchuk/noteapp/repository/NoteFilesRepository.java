package com.prokopchuk.noteapp.repository;

import com.prokopchuk.noteapp.domain.NoteFile;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteFilesRepository extends JpaRepository<NoteFile, Long> {

    List<NoteFile> findAllByNoteId(Long noteId);

    void deleteAllByNoteId(Long noteId);

}
