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
    
    public void acceptFriendRequest(Long userId, Long friendId) {
        User user = userService.findUserById(userId);
        User friend = userService.findUserById(friendId);
        
        Friendship friendship = friendshipRepository.findByUserAndFriend(friend, user);
        if (friendship != null && friendship.getStatus().equals(FriendshipStatus.PENDING)) {
            friendship.setStatus(FriendshipStatus.ACCEPTED);
            friendshipRepository.save(friendship);
        }
    }
    
    public void rejectFriendRequest(Long userId, Long friendId) {
        User user = userService.findUserById(userId);
        User friend = userService.findUserById(friendId);
        
        Friendship friendship = friendshipRepository.findByUserAndFriend(friend, user);
        
        if (friendship != null && friendship.getStatus().equals(FriendshipStatus.PENDING)) 
        {
            friendship.setStatus(FriendshipStatus.REJECTED);
            friendshipRepository.save(friendship);
        }
    }
    
    public Iterable<Friendship> getPendingRequestsForUser(Long userId) {
        User user = userService.findUserById(userId);
        return friendshipRepository.findAllByFriendAndStatus(user, FriendshipStatus.PENDING);
    }
    
    public Iterable<Friendship> getFriendsOfUser(Long userId) {
        User user = userService.findUserById(userId);
        return friendshipRepository.findAllByUserAndStatusOrFriendAndStatus(user, FriendshipStatus.ACCEPTED, user, FriendshipStatus.ACCEPTED);
    }
}