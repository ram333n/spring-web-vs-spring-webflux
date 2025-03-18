package com.prokopchuk.reactivenoteapp.web;

import com.prokopchuk.commons.api.ApiResponse;
import com.prokopchuk.commons.api.Responses;
import com.prokopchuk.commons.dto.UserDto;
import com.prokopchuk.commons.exception.NotFoundException;
import com.prokopchuk.reactivenoteapp.gateway.NoteAppGateway;
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
import reactor.core.publisher.Mono;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserController {

    private final NoteAppGateway noteAppGateway;

    @GetMapping("/users/{id}")
    public Mono<ApiResponse<UserDto>> getUserById(@PathVariable("id") Long id) {
        return Mono.just(id)
            .doOnNext(userId -> log.info("Request on retrieving user by id. Id: {}", userId))
            .flatMap(noteAppGateway::getUserById)
            .map(Responses::ok)
            .switchIfEmpty(Mono.error(new NotFoundException(String.format("User with id %s not found", id))));
    }

    @PostMapping("/users")
    public Mono<ResponseEntity<ApiResponse<Long>>> createUser(@RequestBody Mono<UserDto> userDtoMono) {
        return userDtoMono
            .doOnNext(userDto -> log.info("Request on creating user. User: {}", userDto))
            .flatMap(noteAppGateway::createUser)
            .map(id -> new ResponseEntity<>(Responses.created(id), HttpStatus.CREATED));
    }

    @PutMapping("/users/{id}")
    public Mono<ApiResponse<Long>> updateUser(@PathVariable("id") Long id, @RequestBody Mono<UserDto> userDtoMono) {
        return userDtoMono
            .doOnNext(userDto -> log.info("Request on updating user. Id: {}, User: {}", id, userDto))
            .flatMap(userDto -> noteAppGateway.updateUser(id, userDto))
            .map(Responses::ok);
    }

    @DeleteMapping("/users/{id}")
    public Mono<ResponseEntity<ApiResponse<Void>>> deleteUser(@PathVariable("id") Long id) {
        log.info("Request on deleting user. Id: {}", id);

        return Mono.just(id)
            .doOnNext(userId -> log.info("Request on deleting user. Id: {}", userId))
            .flatMap(userId -> noteAppGateway.deleteUserById(id))
            .map(deleted -> deleted
                ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                : new ResponseEntity<>(Responses.notFound(String.format("User with id: %s not found", id)), HttpStatus.NOT_FOUND)
            );
    }

}
