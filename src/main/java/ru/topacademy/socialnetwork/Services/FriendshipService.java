package ru.topacademy.socialnetwork.Services;

import ru.topacademy.socialnetwork.Models.*;
import ru.topacademy.socialnetwork.Repositories.*;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendshipService {
    
    @Autowired
    private FriendshipRepository friendshipRepository;
    
    @Autowired
    private UserService userService;
    
    public void sendFriendRequest(Long userId, Long friendId) {
        User user = userService.findUserById(userId);
        User friend = userService.findUserById(friendId);
        
        if (!user.equals(friend)) 
        {
            Friendship friendship = new Friendship(user, friend, LocalDateTime.now(), FriendshipStatus.PENDING);
            friendshipRepository.save(friendship);
        }
    }
    
    public void acceptFriendRequest(User currentUser, Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("Friendship not found"));

        if (friendship.getFriend().equals(currentUser) && friendship.getStatus() == FriendshipStatus.PENDING) {
            friendship.setStatus(FriendshipStatus.ACCEPTED);
            friendship.setCreatedAt(LocalDateTime.now());
            friendshipRepository.save(friendship);
        }
    }

    public void rejectFriendRequest(User currentUser, Long friendshipId) {
        Friendship friendship = friendshipRepository.findById(friendshipId)
                .orElseThrow(() -> new RuntimeException("Friendship not found"));

        if (friendship.getFriend().equals(currentUser) && friendship.getStatus() == FriendshipStatus.PENDING) {
            friendshipRepository.delete(friendship);
        }
    }
    
    public void removeFriend(User currentUser, Long friendId) {
        Friendship friendship = friendshipRepository.findByUserAndFriend(currentUser.getId(), friendId)
                .orElseThrow(() -> new RuntimeException("Friendship not found"));

        friendshipRepository.delete(friendship);
    }
}