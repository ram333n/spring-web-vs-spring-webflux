package com.prokopchuk.noteapp.service.impl;

import com.prokopchuk.commons.dto.UserDto;
import com.prokopchuk.commons.exception.BadRequestException;
import com.prokopchuk.noteapp.domain.User;
import com.prokopchuk.noteapp.repository.UserRepository;
import com.prokopchuk.noteapp.service.UserService;
import com.prokopchuk.noteapp.service.mapper.UserMapper;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;


    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
    public Long createUser(UserDto userDto) {
        if (userRepository.existsByName(userDto.getName())) {
            throw new BadRequestException(String.format("User with name %s already exists", userDto.getName()));
        }

        User savedUser = userRepository.save(mapper.toEntity(userDto));

        return savedUser.getId();
    }

    @Override
    public Long updateUser(Long id, UserDto userDto) {
        validateUserUpdate(id, userDto);

        User entityToUpdate = userRepository.findById(id)
            .orElseThrow(() -> new BadRequestException(String.format("User with id %s not found", id)));

        mapper.map(userDto, entityToUpdate);
        userRepository.save(entityToUpdate);

        return userDto.getId();
    }

    private void validateUserUpdate(Long id, UserDto userDto) {
        if (Objects.isNull(userDto.getName())) {
            throw new BadRequestException("User must have a name to update");
        }

        if (!userRepository.existsById(id)) {
            throw new BadRequestException(String.format("User with id %s does not exist", id));
        }

        if (userRepository.existsByNameAndIdNot(userDto.getName(), id)) {
            throw new BadRequestException(String.format("User with name %s already exists", userDto.getName()));
        }
    }

    @Override
    @Transactional
    public boolean deleteUserById(Long id) {
        return userRepository.deleteUserById(id) > 0L;
    }

}
