package springsecurity1.demo.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import springsecurity1.demo.models.MyUser;

@Controller
public class AdminController {

    @GetMapping("/admin/home")
    public String adminPage(@AuthenticationPrincipal MyUser user , Model model){
        if(user.getRoles().stream().noneMatch(role -> role.getName().equals("ROLE_ADMIN"))){
            System.out.println("У вас нету прав для доступа к данному URL");
            return "redirect:/user/home";
        }

        model.addAttribute("user", user);
        return "admin";
    }
}
