package com.prokopchuk.reactivenoteapp.repository;


import com.prokopchuk.reactivenoteapp.domain.User;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<User, Long> {

    Mono<Boolean> existsByName(String name);

    Mono<Boolean> existsByNameAndIdNot(String name, Long id);

    Mono<Long> deleteUserById(Long id);

}
