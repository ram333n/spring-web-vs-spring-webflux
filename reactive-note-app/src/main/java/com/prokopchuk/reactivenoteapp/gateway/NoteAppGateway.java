package com.prokopchuk.reactivenoteapp.gateway;

import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.commons.dto.NoteFileDto;
import com.prokopchuk.commons.dto.UserDto;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

public interface NoteAppGateway {

    Mono<UserDto> getUserById(Long id);

    Mono<Long> createUser(UserDto userDto);

    Mono<Long> updateUser(Long id, UserDto userDto);

    Mono<Boolean> deleteUserById(Long userId);

    Mono<NoteDto> getNoteById(Long id);

    Mono<Long> createNote(NoteDto noteDto);

    Mono<Long> updateNote(Long id, NoteDto noteDto);

    Mono<Boolean> deleteNoteById(Long id);

    Mono<Long> uploadNoteFile(Long noteId, FilePart file);

    Mono<NoteFileDto> getNoteFileByNoteIdAndFileId(Long noteId, Long fileId);

    Mono<Void> deleteNoteFileByNoteIdAndFileId(Long noteId, Long fileId);

}
