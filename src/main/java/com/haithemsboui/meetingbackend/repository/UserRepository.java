package com.haithemsboui.meetingbackend.repository;

import com.haithemsboui.meetingbackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    void deleteById(Long id);

    @Override
    boolean existsById(Long id);


}
