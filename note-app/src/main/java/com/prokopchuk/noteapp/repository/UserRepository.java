package com.prokopchuk.noteapp.repository;

import com.prokopchuk.noteapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
