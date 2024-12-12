package ru.topacademy.socialnetwork.Repositories;

import ru.topacademy.socialnetwork.Models.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
	 @Query("SELECT p FROM Post p WHERE p.user IN :friends ORDER BY p.createdAt DESC")
	    List<Post> findPostsByFriends(@Param("friends") List<User> friends);
}