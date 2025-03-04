package springsecurity1.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springsecurity1.demo.models.MyUser;

@Controller
public class UserController {

    @GetMapping("/user/home")
    public String userPage(@AuthenticationPrincipal MyUser user, Model model) {
        model.addAttribute("user", user);
        return "user";
    }
}
