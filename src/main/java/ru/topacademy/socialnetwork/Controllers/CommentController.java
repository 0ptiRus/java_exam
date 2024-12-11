package ru.topacademy.socialnetwork.Controllers;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.topacademy.socialnetwork.Models.*;
import ru.topacademy.socialnetwork.Services.*;

@ComponentScan
@Controller
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;
    
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public String createComment(@RequestParam Long postId, @ModelAttribute Comment comment, Model model) {
        Post post = postService.getPostById(postId); 

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User authenticatedUser = userService.findUserByEmail(auth.getName());
        comment.setUser(authenticatedUser);
        comment.setPost(post); 
        comment.setCreatedAt(LocalDateTime.now());

        commentService.createComment(comment); 

        return "redirect:/feed"; 
    }
}