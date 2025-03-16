package com.prokopchuk.noteapp.web;

import com.prokopchuk.commons.api.ApiResponse;
import com.prokopchuk.commons.api.Responses;
import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.commons.dto.NoteFileDto;
import com.prokopchuk.commons.exception.NotFoundException;
import com.prokopchuk.noteapp.gateway.NoteAppGateway;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteAppGateway noteAppGateway;

    @GetMapping("/notes/{id}")
    public ApiResponse<NoteDto> getNoteById(@PathVariable("id") Long id) {
        log.info("Request on retrieving note by id. Id: {}", id);

        return noteAppGateway.getNoteById(id)
            .map(Responses::ok)
            .orElseThrow(() -> new NotFoundException(String.format("Note with id: %s doesn't exist", id)));
    }

    @PostMapping("/notes")
    public ResponseEntity<ApiResponse<Long>> createNote(@RequestBody NoteDto noteDto) {
        log.info("Request on creating note. Note: {}", noteDto);

        return new ResponseEntity<>(
            Responses.created(noteAppGateway.createNote(noteDto)),
            HttpStatus.CREATED
        );
    }

    @PutMapping("/notes/{id}")
    public ApiResponse<Long> updateNote(@PathVariable("id") Long id, @RequestBody NoteDto noteDto) {
        log.info("Request on updating note. Id: {}, Note: {}", id, noteDto);

        return Responses.ok(noteAppGateway.updateNote(id, noteDto));
    }

    @DeleteMapping("/notes/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNote(@PathVariable("id") Long id) {
        log.info("Request on deleting note. Id: {}", id);

        return noteAppGateway.deleteNoteById(id)
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
            : new ResponseEntity<>(Responses.notFound(String.format("Note with id: %s not found", id)), HttpStatus.NOT_FOUND);
    }

    @PostMapping("/notes/{id}/files")
    public ResponseEntity<ApiResponse<Long>> uploadNoteFile(
        @PathVariable("id") Long noteId,
        @RequestPart("file") MultipartFile file
    ) {
        log.info("Request on uploading note file. Note id: {}, filename: {}", noteId, file.getOriginalFilename());

        return new ResponseEntity<>(
            Responses.created(noteAppGateway.uploadNoteFile(noteId, file)),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/notes/{note-id}/files/{file-id}")
    public ApiResponse<NoteFileDto> getNoteFileByNoteIdAndFileId(
        @PathVariable("note-id") Long noteId,
        @PathVariable("file-id") Long fileId
    ) {
        log.info("Request on retrieving info about note file. Note id: {}, file id: {}", noteId, fileId);

        return noteAppGateway.getNoteFileByNoteIdAndFileId(noteId, fileId)
            .map(Responses::ok)
            .orElseThrow(() -> new NotFoundException(String.format("Note with id: %s does not have file with id: %s", noteId, fileId)));
    }

    @DeleteMapping("/notes/{note-id}/files/{file-id}")
    public ResponseEntity<ApiResponse<Void>> deleteNoteFileByNoteIdAndFileId(
        @PathVariable("note-id") Long noteId,
        @PathVariable("file-id") Long fileId
    ) {
        log.info("Request on deleting file in note. Note id: {}, file id: {}", noteId, fileId);
        noteAppGateway.deleteNoteFileByNoteIdAndFileId(noteId, fileId);

        return new ResponseEntity<>(Responses.noContent(), HttpStatus.NO_CONTENT);
    }

}
