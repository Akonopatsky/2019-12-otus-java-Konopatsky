package ru.otus.hw14.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.hw14.dataaccsess.core.model.User;
import ru.otus.hw14.dataaccsess.core.service.DBServiceUser;


import java.util.List;

@Controller
public class UserController {

    private final DBServiceUser dbServiceUser;

    public UserController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }


    @GetMapping({"/", "/user/list"})
    public String userListView(Model model) {
        List<User> users = dbServiceUser.getAllUsers();
        User newuser = new User("Постовой", 25, "тестовая", "000-00000");
        model.addAttribute("createdUser", newuser);
        model.addAttribute("users", users);
        return "admin.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(@ModelAttribute User user, Model model) {
        dbServiceUser.saveUser(user);
        return new RedirectView("/", true);
    }

    @GetMapping("/user/save")
    public RedirectView userSaveget(@ModelAttribute User user, Model model) {
        dbServiceUser.saveUser(user);
        return new RedirectView("/", true);
    }

}
