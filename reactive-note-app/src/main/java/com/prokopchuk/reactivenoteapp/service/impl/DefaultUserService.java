package com.prokopchuk.reactivenoteapp.service.impl;

import com.prokopchuk.commons.dto.UserDto;
import com.prokopchuk.commons.exception.BadRequestException;
import com.prokopchuk.reactivenoteapp.domain.User;
import com.prokopchuk.reactivenoteapp.repository.UserRepository;
import com.prokopchuk.reactivenoteapp.service.UserService;
import com.prokopchuk.reactivenoteapp.service.mapper.UserMapper;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public Mono<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
            .map(mapper::toDto);
    }

    @Override
    public Mono<Long> createUser(UserDto userDto) {
        return validateUserNameUniqueness(userDto.getName())
            .map(validated -> mapper.toEntity(userDto))
            .flatMap(userRepository::save)
            .map(User::getId);
    }

    private Mono<Boolean> validateUserNameUniqueness(String name) {
        return userRepository.existsByName(name)
            .flatMap(exists -> {
                if (exists) {
                    return Mono.error(new BadRequestException(String.format("User with name %s already exists", name)));
                }

                return Mono.just(true);
            });
    }

    @Override
    public Mono<Long> updateUser(Long id, UserDto userDto) {
        return validateUserUpdate(id, userDto)
            .flatMap(validated -> userRepository.findById(id))
            .switchIfEmpty(Mono.error(new BadRequestException(String.format("User with id %s not found", id))))
            .doOnNext(entityToUpdate -> mapper.map(userDto, entityToUpdate))
            .flatMap(userRepository::save)
            .map(User::getId);
    }

    private Mono<Boolean> validateUserUpdate(Long id, UserDto userDto) {
        return validateUserNamePresence(userDto)
            .flatMap(validated -> validateUserExistenceById(id))
            .flatMap(validated -> validateUserNameUniquenessOnUpdate(userDto.getName(), id))
            .map(validated -> true);
    }

    private Mono<Boolean> validateUserNamePresence(UserDto userDto) {
        if (Objects.isNull(userDto.getName())) {
            return Mono.error(new BadRequestException("User must have a name to update"));
        }

        return Mono.just(true);
    }

    private Mono<Boolean> validateUserExistenceById(Long userId) {
        return userRepository.existsById(userId)
            .flatMap(exists -> {
                if (!exists) {
                    return Mono.error(new BadRequestException(String.format("User with id %s does not exist", userId)));
                }

                return Mono.just(true);
            });
    }

    private Mono<Boolean> validateUserNameUniquenessOnUpdate(String nameToUpdate, Long id) {
        return userRepository.existsByNameAndIdNot(nameToUpdate, id)
            .flatMap(exists -> {
                if (exists) {
                    return Mono.error(new BadRequestException(String.format("User with name %s already exists", nameToUpdate)));
                }

                return Mono.just(true);
            });
    }

    @Override
    public Mono<Boolean> deleteUserById(Long id) {
        return userRepository.deleteUserById(id)
            .map(rowsDeleted -> rowsDeleted > 0);
    }

    @Override
    public Mono<Boolean> existsUserById(Long id) {
        return userRepository.existsById(id);
    }

}
