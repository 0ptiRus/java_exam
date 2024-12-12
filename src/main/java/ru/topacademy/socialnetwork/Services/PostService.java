package ru.topacademy.socialnetwork.Services;

import ru.topacademy.socialnetwork.Models.*;
import ru.topacademy.socialnetwork.Repositories.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.EntityGraph;
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
    
    @EntityGraph(attributePaths = {"comments.user"})
    public Iterable<Post> getAllPosts() {
        return postRepository.findAll();
    }
    
    public void repost(Long postId, Long userId) {
        Post originalPost = postRepository.findById(postId).orElseThrow(() -> new RuntimeException("Пост не найден"));
        User reposterUser = userService.findUserById(userId);
        
        Post repostedPost = new Post(reposterUser, originalPost.getContent(), LocalDateTime.now(), originalPost, true);
        
        postRepository.save(repostedPost);
    }

    @EntityGraph(attributePaths = {"comments", "likes", "reposts"})
	public Post getPostById(Long postId) {
		return postRepository.getById(postId);
	}
    
    public List<Post> getFriendsPosts(List<User> friends) {
        return postRepository.findPostsByFriends(friends);
    }
    
    public String deletePost(Post post)
    {
    	try
    	{
    		postRepository.delete(post);    		
    	}
    	catch(Exception ex)
    	{
    		return ex.toString();
    	}
    	return "OK";
    }
}