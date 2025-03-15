package com.prokopchuk.noteapp.service;

import com.prokopchuk.commons.dto.UserDto;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> getUserById(Long id);

    Long createUser(UserDto userDto);

    Long updateUser(Long id, UserDto userDto);

    boolean deleteUserById(Long id);

    boolean existsUserById(Long id);

}
