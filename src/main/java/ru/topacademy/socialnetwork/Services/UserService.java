package ru.topacademy.socialnetwork.Services;

import ru.topacademy.socialnetwork.Models.*;
import ru.topacademy.socialnetwork.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void registerUser(User user) {
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
}