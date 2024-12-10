package ru.topacademy.socialnetwork.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;
import ru.topacademy.socialnetwork.Models.*;
import ru.topacademy.socialnetwork.Security.JwtUtil;
import ru.topacademy.socialnetwork.Services.*;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
//	@Autowired
//    private JwtUtil jwtUtil;
//	
//	@Autowired
//	private AuthenticationManager authenticationManager;
	
	@GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) 
	{
        if (error != null) 
        {
            model.addAttribute("errorMessage", "Invalid username or password!");
        }
        if (logout != null) 
        {
            model.addAttribute("successMessage", "You have been logged out successfully.");
        }
        model.addAttribute("user", new User());
        return "login"; 
    }
    
    @GetMapping("/register")
    public String registration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register")
    public String registration(Model model, @Valid @ModelAttribute User user, BindingResult bindingResult) 
    {
        if (bindingResult.hasErrors()) 
        {
            model.addAttribute("user", user); 
            return "register";
        }
        
        if(!user.getPassword().equals(user.getConfirmPassword()))
        {
        	bindingResult.rejectValue("confirmPassword", "error.user", "Пароли не совпадают.");
        	return "register";
        }

        try 
        {
            userService.registerUser(user);
            return "redirect:/login";
        } 
        catch (IllegalArgumentException e) 
        {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("user", user);
            return "register";
        }
    }
}
