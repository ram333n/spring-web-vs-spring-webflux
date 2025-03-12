package com.prokopchuk.noteapp.web;

import com.prokopchuk.commons.api.ApiResponse;
import com.prokopchuk.commons.api.Responses;
import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.noteapp.service.NoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/notes/{id}")
    public ApiResponse<NoteDto> getNoteById(@PathVariable("id") Long id) {
        log.info("Request on retrieving note by id. Id: {}", id);

        throw new UnsupportedOperationException();
    }

    @PostMapping("/notes")
    public ResponseEntity<ApiResponse<Long>> createNote(@RequestBody NoteDto noteDto) {
        log.info("Request on creating note. Note: {}", noteDto);

        return new ResponseEntity<>(
            Responses.created(noteService.createNote(noteDto)),
            HttpStatus.CREATED
        );
    }

    @PutMapping("/notes/{id}")
    public ApiResponse<Long> updateNote(@PathVariable("id") Long id, @RequestBody NoteDto noteDto) {
        log.info("Request on updating note. Id: {}, Note: {}", id, noteDto);

        return Responses.ok(noteService.updateNote(id, noteDto));
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable("id") Long id) {
        log.info("Request on deleting note. Id: {}", id);

        return noteService.deleteNoteById(id)
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
            : new ResponseEntity<>(Responses.notFound(String.format("Note with id: %s not found", id)), HttpStatus.NOT_FOUND);
    }

}
