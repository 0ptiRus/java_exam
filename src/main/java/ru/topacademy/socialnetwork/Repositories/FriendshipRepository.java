package ru.topacademy.socialnetwork.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.topacademy.socialnetwork.Models.*;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    
    boolean existsByUserAndFriend(User user, User friend);

    List<Friendship> findByUserAndStatus(User user, FriendshipStatus status);

    List<Friendship> findByFriendAndStatus(User friend, FriendshipStatus status);

    @Query("SELECT f.friend FROM Friendship f WHERE f.user = :user AND f.status = :status")
    List<User> findFriendsInitiatedByUser(@Param("user") User user, @Param("status") FriendshipStatus status);
    
    @Query("SELECT f.user FROM Friendship f WHERE f.friend = :user AND f.status = :status")
    List<User> findFriendsReceivedByUser(@Param("user") User user, @Param("status") FriendshipStatus status);

    @Query("SELECT f FROM Friendship f WHERE f.user.id = :userId AND f.friend.id = :friendId")
    Optional<Friendship> findByUserAndFriend(@Param("userId") Long userId, @Param("friendId") Long friendId);

}
