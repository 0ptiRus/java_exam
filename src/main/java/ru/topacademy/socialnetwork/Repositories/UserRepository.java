package ru.topacademy.socialnetwork.Repositories;

import ru.topacademy.socialnetwork.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findByEmail(String email);
}