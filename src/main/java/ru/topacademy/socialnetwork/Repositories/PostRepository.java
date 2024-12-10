package ru.topacademy.socialnetwork.Repositories;

import ru.topacademy.socialnetwork.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}