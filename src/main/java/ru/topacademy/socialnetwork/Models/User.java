package ru.topacademy.socialnetwork.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="users")
@Data
public class User {
    
    @Id
    @GeneratedValue()
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false, unique = true)
    private String email;
    
    @Column(nullable = false)
    private String password;
    
    @OneToMany(mappedBy = "user")
    private List<Post> posts;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public User() {
		super();
	}
    
    
    
    // Getters, setters, constructors
}