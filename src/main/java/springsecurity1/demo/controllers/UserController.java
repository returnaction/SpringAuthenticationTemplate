package springsecurity1.demo.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springsecurity1.demo.models.Role;
import springsecurity1.demo.models.User;

@Controller
public class UserController {

    @GetMapping("/user/home")
    public String userPage(@AuthenticationPrincipal User user, Model model) {

        if (user != null) {
            System.out.println("User: " + user.getUsername());  // Проверка, что объект передается
        } else {
            System.out.println("No user found.");
        }
        assert user != null;
        user.getRoles().forEach(Role::getName);
        model.addAttribute("user", user);
        return "user";
    }


}
