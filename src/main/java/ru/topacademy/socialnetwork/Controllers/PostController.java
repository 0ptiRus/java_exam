package ru.topacademy.socialnetwork.Controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@GetMapping("/test")
	public String test() {
	    return "<h1>Test</h1>"; // Create a simple test view to see if the controller is working
	}
}
