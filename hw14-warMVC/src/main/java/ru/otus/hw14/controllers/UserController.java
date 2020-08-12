package ru.otus.hw14.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.hw14.dataaccsess.core.model.User;
import ru.otus.hw14.dataaccsess.core.service.DBServiceUser;
import ru.otus.hw14.services.UserCreationDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@SessionAttributes("userDto")
public class UserController {

    private final DBServiceUser dbServiceUser;

    public UserController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @GetMapping({"/", "/user/list"})
    public String userListView(
            Model model,
            @ModelAttribute("userDto") UserCreationDto userDto) {
        model.addAttribute("userDto", userDto);
        List<User> users = dbServiceUser.getAllUsers();
        model.addAttribute("users", users);
        return "admin.html";
    }

    @PostMapping("/user/save")
    public RedirectView userSave(
            @ModelAttribute UserCreationDto userDto,
            RedirectAttributes redirectAttributes) {
        dbServiceUser.saveUser(userDto.createUser());
        userDto = new UserCreationDto();
        redirectAttributes.addFlashAttribute("userDto", userDto);
        return new RedirectView("/", true);
    }

    @PostMapping("/user/addPhone")
    public RedirectView userAddPhone(
            @ModelAttribute("userDto") UserCreationDto userDto,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request) {
        userDto.addBlankPhone();
        redirectAttributes.addFlashAttribute("userDto", userDto);
        return new RedirectView(request.getHeader("referer"), true);
    }

    @GetMapping("/user/create")
    public String userSaveSeparate(
            Model model,
            @ModelAttribute("userDto") UserCreationDto userDto) {
        model.addAttribute("userDto", userDto);
        return "userCreate.html";
    }

    @ModelAttribute("userDto")
    public UserCreationDto userDto() {
        return new UserCreationDto();
    }

}
