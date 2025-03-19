package com.prokopchuk.reactivenoteapp.web;

import com.prokopchuk.commons.api.ApiResponse;
import com.prokopchuk.commons.api.Responses;
import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.commons.dto.NoteFileDto;
import com.prokopchuk.commons.exception.NotFoundException;
import com.prokopchuk.reactivenoteapp.gateway.NoteAppGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequiredArgsConstructor
public class NoteController {

    private final NoteAppGateway noteAppGateway;

    @GetMapping("/notes/{id}")
    public Mono<ApiResponse<NoteDto>> getNoteById(@PathVariable("id") Long id) {
        return Mono.just(id)
            .doOnNext(noteId -> log.info("Request on retrieving note by id. Id: {}", noteId))
            .flatMap(noteAppGateway::getNoteById)
            .map(Responses::ok)
            .switchIfEmpty(Mono.error(new NotFoundException(String.format("Note with id %s doesn't exist", id))));
    }

    @PostMapping("/notes")
    public Mono<ResponseEntity<ApiResponse<Long>>> createNote(@RequestBody Mono<NoteDto> noteDtoMono) {
        return noteDtoMono
            .doOnNext(noteDto -> log.info("Request on creating note. Note: {}", noteDto))
            .flatMap(noteAppGateway::createNote)
            .map(noteId -> new ResponseEntity<>(Responses.created(noteId), HttpStatus.CREATED));
    }

    @PutMapping("/notes/{id}")
    public Mono<ApiResponse<Long>> updateNote(@PathVariable("id") Long id, @RequestBody Mono<NoteDto> noteDtoMono) {
        return noteDtoMono
            .doOnNext(noteDto -> log.info("Request on updating note. Id: {}, Note: {}", id, noteDto))
            .flatMap(noteDto -> noteAppGateway.updateNote(id, noteDto))
            .map(Responses::ok);
    }

    @DeleteMapping("/notes/{id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> deleteNote(@PathVariable("id") Long id) {
        return Mono.just(id)
            .doOnNext(noteId -> log.info("Request on deleting note. Id: {}", noteId))
            .flatMap(noteAppGateway::deleteNoteById)
            .map(deleted -> deleted
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(Responses.notFound(String.format("Note with id: %s not found", id)), HttpStatus.NOT_FOUND)
            );
    }

    @PostMapping("/notes/{id}/files")
    public Mono<ResponseEntity<ApiResponse<Long>>> uploadNoteFile(
        @PathVariable("id") Long noteId,
        @RequestPart("file") Mono<FilePart> fileMono
    ) {
        return fileMono
            .doOnNext(file -> log.info("Request on uploading note file. Note id: {}, filename: {}", noteId, file.filename()))
            .flatMap(file -> noteAppGateway.uploadNoteFile(noteId, file))
            .map(fileId -> new ResponseEntity<>(Responses.created(fileId), HttpStatus.CREATED));
    }

    @GetMapping("/notes/{note-id}/files/{file-id}")
    public Mono<ApiResponse<NoteFileDto>> getNoteFileByNoteIdAndFileId(
        @PathVariable("note-id") Long noteId,
        @PathVariable("file-id") Long fileId
    ) {
        return noteAppGateway.getNoteFileByNoteIdAndFileId(noteId, fileId)
            .doFirst(() -> log.info("Request on retrieving info about note file. Note id: {}, file id: {}", noteId, fileId))
            .map(Responses::ok)
            .switchIfEmpty(Mono.error(new NotFoundException(String.format("Note with id: %s does not have file with id: %s", noteId, fileId))));
    }

    @DeleteMapping("/notes/{note-id}/files/{file-id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> deleteNoteFileByNoteIdAndFileId(
        @PathVariable("note-id") Long noteId,
        @PathVariable("file-id") Long fileId
    ) {
        return noteAppGateway.deleteNoteFileByNoteIdAndFileId(noteId, fileId)
            .doFirst(() -> log.info("Request on deleting file in note. Note id: {}, file id: {}", noteId, fileId))
            .thenReturn(new ResponseEntity<>(Responses.noContent(), HttpStatus.NO_CONTENT));
    }

}
