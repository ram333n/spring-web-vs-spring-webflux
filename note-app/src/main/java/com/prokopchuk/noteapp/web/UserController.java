package com.prokopchuk.noteapp.web;

import com.prokopchuk.commons.api.ApiResponse;
import com.prokopchuk.commons.api.Responses;
import com.prokopchuk.commons.dto.UserDto;
import com.prokopchuk.commons.exception.NotFoundException;
import com.prokopchuk.noteapp.gateway.NoteAppGateway;
import com.prokopchuk.noteapp.service.UserService;
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
public class UserController {

    private final NoteAppGateway noteAppGateway;

    @GetMapping("/users/{id}")
    public ApiResponse<UserDto> getUserById(@PathVariable("id") Long id) {
        log.info("Request on retrieving user by id. Id: {}", id);

        return noteAppGateway.getUserById(id)
            .map(Responses::ok)
            .orElseThrow(() -> new NotFoundException(String.format("User with id %s not found", id)));
    }

    @PostMapping("/users")
    public ResponseEntity<ApiResponse<Long>> createUser(@RequestBody UserDto userDto) {
        log.info("Request on creating user. User: {}", userDto);

        return new ResponseEntity<>(
            Responses.created(noteAppGateway.createUser(userDto)),
            HttpStatus.CREATED
        );
    }

    @PutMapping("/users/{id}")
    public ApiResponse<Long> updateUser(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
        log.info("Request on updating user. Id: {}, User: {}", id, userDto);

        return Responses.ok(noteAppGateway.updateUser(id, userDto));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable("id") Long id) {
        log.info("Request on deleting user. Id: {}", id);

        return noteAppGateway.deleteUserById(id)
            ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
            : new ResponseEntity<>(Responses.notFound(String.format("User with id: %s not found", id)), HttpStatus.NOT_FOUND);
    }

}
