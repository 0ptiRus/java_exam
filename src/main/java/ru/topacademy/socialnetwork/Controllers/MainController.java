package ru.topacademy.socialnetwork.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ru.topacademy.socialnetwork.Models.Post;
import ru.topacademy.socialnetwork.Models.User;
import ru.topacademy.socialnetwork.Services.*;

@ComponentScan
@Controller
public class MainController {

    private final PostService postService;
    
    @Autowired
    private UserService userService;

    public MainController(PostService postService) {
        this.postService = postService;}
 
    @GetMapping("/feed")
    public String feed(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());

        List<Post> friendsPosts = postService.getFriendsPosts(userService.getFriends(currentUser));

        model.addAttribute("posts", friendsPosts);
        return "feed";
    }
    
    @GetMapping("/profile")
    public String profile(Model model)
    {
    	 Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         User authenticatedUser = userService.findUserByEmail(auth.getName()); 
         
         model.addAttribute("user", authenticatedUser);
         model.addAttribute("posts", authenticatedUser.getPosts());
         
         return "profile";
    }
    
}
