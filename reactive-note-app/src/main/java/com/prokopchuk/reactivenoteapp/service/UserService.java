package com.prokopchuk.reactivenoteapp.service;

import com.prokopchuk.commons.dto.UserDto;
import reactor.core.publisher.Mono;

public interface UserService {

    Mono<UserDto> getUserById(Long id);

    Mono<Long> createUser(UserDto userDto);

    Mono<Long> updateUser(Long id, UserDto userDto);

    Mono<Boolean> deleteUserById(Long id);

    Mono<Boolean> existsUserById(Long id);

}
