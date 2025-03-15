package com.prokopchuk.noteapp.gateway;

import com.prokopchuk.commons.dto.NoteDto;
import com.prokopchuk.commons.dto.UserDto;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface NoteAppGateway {

    Optional<UserDto> getUserById(Long id);

    Long createUser(UserDto userDto);

    Long updateUser(Long id, UserDto userDto);

    boolean deleteUserById(Long userId);

    Optional<NoteDto> getNoteById(Long id);

    Long createNote(NoteDto noteDto);

    Long updateNote(Long id, NoteDto noteDto);

    boolean deleteNoteById(Long id);

    Long uploadNoteFile(Long noteId, MultipartFile file);
}
