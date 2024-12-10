package ru.topacademy.socialnetwork.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post {
    
    @Id
    @GeneratedValue()
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    
    @Column(nullable = false)
    private String content;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @Column(nullable = false)
    private Integer repostCount = 0;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "original_post_id")
    private Post originalPost;
    
    @Column(nullable = true)
    private Boolean isRepost = false;

    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "post")
    private List<Like> likes;
    
    @OneToMany(mappedBy = "originalPost")
    private List<Post> reposts;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public Post(User user, String content, LocalDateTime createdAt) {
		super();
		this.user = user;
		this.content = content;
		this.createdAt = createdAt;
	}

	public Post(User user, String content, LocalDateTime createdAt, Post originalPost, Boolean isRepost) {
		super();
		this.user = user;
		this.content = content;
		this.createdAt = createdAt;
		this.originalPost = originalPost;
		this.isRepost = isRepost;
	}

	public Post() {
		super();
	}
    
    
    
    // Getters, setters, constructors
}