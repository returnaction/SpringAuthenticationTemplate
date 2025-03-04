package springsecurity1.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springsecurity1.demo.models.MyUser;
import springsecurity1.demo.services.UserService;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        model.addAttribute("user", new MyUser());
        return "register";
    }

    @PostMapping("register")
    public String registerUser(MyUser user){
        try{
            userService.registerNewUser(user);
        } catch(IllegalArgumentException e){
            return "redirect:/register";
        }

        return "redirect:/login";
    }
}
