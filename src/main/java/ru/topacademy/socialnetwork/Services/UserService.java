package ru.topacademy.socialnetwork.Services;

import ru.topacademy.socialnetwork.Models.*;
import ru.topacademy.socialnetwork.Repositories.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private FriendshipRepository friendshipRepository;
    
    public void registerUser(User user) throws IllegalArgumentException {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Пользователь с таким именем уже существует.");
        }
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    
    public User findUserById(Long id)
    {
    	return userRepository.findById(id).orElse(null);
    }
    
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
    public void sendFriendRequest(User currentUser, String username) 
    {
        User friend = userRepository.findByUsername(username);
        if (friend != null && !friend.equals(currentUser)) 
        {
            Friendship friendship = new Friendship(currentUser, friend, FriendshipStatus.PENDING);
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
    
    public List<User> getFriends(User user) 
    {
        List<User> initiatedFriends = friendshipRepository.findFriendsInitiatedByUser(user, FriendshipStatus.ACCEPTED);
        List<User> receivedFriends = friendshipRepository.findFriendsReceivedByUser(user, FriendshipStatus.ACCEPTED);

        Set<User> allFriends = new HashSet<>(initiatedFriends);
        allFriends.addAll(receivedFriends);

        return new ArrayList<>(allFriends);
    }


    public List<Friendship> getIncomingRequests(User user) {
        return friendshipRepository.findByFriendAndStatus(user, FriendshipStatus.PENDING);
    }

    public List<Friendship> getOutgoingRequests(User user) {
        return friendshipRepository.findByUserAndStatus(user, FriendshipStatus.PENDING);
    }

}