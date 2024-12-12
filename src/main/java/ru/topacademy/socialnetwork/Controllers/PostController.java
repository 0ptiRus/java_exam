package ru.topacademy.socialnetwork.Controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import ru.topacademy.socialnetwork.Models.Post;
import ru.topacademy.socialnetwork.Models.User;
import ru.topacademy.socialnetwork.Services.*;
@ComponentScan
@Controller
@RequestMapping("/posts")
public class PostController 
{
	@Autowired
	private PostService postService;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create")
	public String createPost(@Valid @ModelAttribute Post post, BindingResult bindingResult, Model model) 
	{
        if (bindingResult.hasErrors()) 
        {
            model.addAttribute("post", post);
            return "redirect:/feed";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String email = auth.getName();
    	
    	User authenticatedUser = userService.findUserByEmail(email);
        
        post.setUser(authenticatedUser);
        post.setCreatedAt(LocalDateTime.now());

        postService.createPost(post);
        return "redirect:/feed"; // Redirect to the main page or posts list
    }
	
	@PostMapping("/delete")
    public String deletePost(@RequestParam("postId") Long postId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());

        Post post = postService.getPostById(postId);

        if (post.getUser().equals(currentUser)) 
        {
            if(postService.deletePost(post).equals("OK"))
            {
            	return "redirect:/profile";        	
            }
        }
        return "/error";

    }
	
}
