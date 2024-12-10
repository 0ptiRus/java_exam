package ru.topacademy.socialnetwork.Security;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ru.topacademy.socialnetwork.Services.UserService;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService; 
    private final PasswordEncoder passwordEncoder;

    public CustomUserDetailsService(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Fetch user from DB
        ru.topacademy.socialnetwork.Models.User user = userService.findUserByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        // Create UserDetails object
        return User.builder()
                   .username(user.getEmail()) // Assume 'email' is the username
                   .password(user.getPassword()) // Ensure passwords are stored encrypted
                   .build();
    }
}
