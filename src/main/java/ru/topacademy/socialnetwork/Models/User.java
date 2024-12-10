package ru.topacademy.socialnetwork.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import ru.topacademy.socialnetwork.extensions.PasswordMatches;

import java.util.List;

@Entity
@Table(name="users")
//@PasswordMatches
public class User {
    
    @Id
    @GeneratedValue()
    private Long id;
    
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Имя пользователя не может быть пустым.")
    private String username;
    
    @Column(nullable = false, unique = true)
    @NotBlank(message = "Email не может быть пустым.")
    @Email(message = "Введите корректный email.")
    private String email;
    
    @Column(nullable = false)
    @Size(min = 5, message = "Пароль должен содержать не менее 5 символов.")
    @Pattern(regexp = ".*\\d.*", message = "Пароль должен содержать хотя бы одну цифру.")
    private String password;
    
    @Transient
    private String confirmPassword;
    
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
	
	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String password) {
		this.confirmPassword = password;
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