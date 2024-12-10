package ru.topacademy.socialnetwork.Services;

import ru.topacademy.socialnetwork.Models.*;
import ru.topacademy.socialnetwork.Repositories.*;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    
	@Autowired
    private PostRepository postRepository;
    
    @Autowired
    private UserService userService;
    
    public void createPost(Post post) {
        postRepository.save(post);
    }
    
    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    public void repost(Long postId, Long userId) {
        Post originalPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Пост не найден"));
        User reposterUser = userService.findUserById(userId);
        
        Post repostedPost = new Post(reposterUser, originalPost.getContent(), LocalDateTime.now(), originalPost, true);
        
        postRepository.save(repostedPost);
    }
}