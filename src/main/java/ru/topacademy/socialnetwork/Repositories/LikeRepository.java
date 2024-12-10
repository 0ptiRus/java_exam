package ru.topacademy.socialnetwork.Repositories;

import ru.topacademy.socialnetwork.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
}