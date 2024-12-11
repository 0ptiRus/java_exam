package ru.topacademy.socialnetwork.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ru.topacademy.socialnetwork.Models.Friendship;
import ru.topacademy.socialnetwork.Models.User;
import ru.topacademy.socialnetwork.Services.UserService;

@ComponentScan
@Controller
@RequestMapping("/friends")
public class FriendsController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public String getFriendsPage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());

        List<User> friends = userService.getFriends(currentUser);
        List<Friendship> incomingRequests = userService.getIncomingRequests(currentUser);
        List<Friendship> outgoingRequests = userService.getOutgoingRequests(currentUser);

        model.addAttribute("friends", friends);
        model.addAttribute("incomingRequests", incomingRequests);
        model.addAttribute("outgoingRequests", outgoingRequests);

        return "friends"; // Returns the "friends.html" page
    }

    @PostMapping("/addFriend")
    public String addFriend(@RequestParam("username") String username) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());

        userService.sendFriendRequest(currentUser, username);

        return "redirect:/friends";
    }

    @PostMapping("/accept")
    public String acceptFriendRequest(@RequestParam("friendshipId") Long friendshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());

        userService.acceptFriendRequest(currentUser, friendshipId);

        return "redirect:/friends";
    }

    @PostMapping("/reject")
    public String rejectFriendRequest(@RequestParam("friendshipId") Long friendshipId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = userService.findUserByEmail(auth.getName());

        userService.rejectFriendRequest(currentUser, friendshipId);

        return "redirect:/friends";
    }
}
