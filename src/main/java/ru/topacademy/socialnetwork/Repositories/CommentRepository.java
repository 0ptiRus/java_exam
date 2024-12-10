package ru.topacademy.socialnetwork.Repositories;

import ru.topacademy.socialnetwork.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}