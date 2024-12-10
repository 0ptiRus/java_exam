package ru.topacademy.socialnetwork.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.topacademy.socialnetwork.Models.*;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    Friendship findByUserAndFriend(User user, User friend);

	Iterable<Friendship> findAllByFriendAndStatus(User user, FriendshipStatus status);

	Iterable<Friendship> findAllByUserAndStatusOrFriendAndStatus(User user, FriendshipStatus status, User user2,
			FriendshipStatus status2);
}
