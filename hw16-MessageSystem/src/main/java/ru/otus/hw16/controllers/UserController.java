//package ru.otus.hw16.controllers;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.servlet.view.RedirectView;
//import ru.otus.hw16.messageSystemApp.front.FrontendService;
//import ru.otus.hw16.services.UserCreationDto;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.List;
//
//@Controller
//@SessionAttributes("userDto")
//public class UserController {
//    private final Logger logger = LoggerFactory.getLogger(UserController.class);
//    private final FrontendService frontendService;
//    private List users;
//
//    public UserController(FrontendService frontendService) {
//        this.frontendService = frontendService;
//    }
//
//
//    @GetMapping({"/", "/user/list"})
//    public String userListView(
//            Model model,
//            @ModelAttribute("userDto") UserCreationDto userDto) {
//        model.addAttribute("userDto", userDto);
//        frontendService.getAllUsers(data -> {
//            users = data.getData();
//            logger.info("all users: {}", data.getData());
//        });
//        model.addAttribute("users", users);
//        return "admin.html";
//    }
//
//    @PostMapping("/user/save")
//    public RedirectView userSave(
//            @ModelAttribute UserCreationDto userDto,
//            RedirectAttributes redirectAttributes) {
//        frontendService.saveUser(userDto.createUser(), data -> logger.info("saved user: {}", data.getData()));
//        userDto = new UserCreationDto();
//        redirectAttributes.addFlashAttribute("userDto", userDto);
//        return new RedirectView("/", true);
//    }
//
//    @PostMapping("/user/addPhone")
//    public RedirectView userAddPhone(
//            @ModelAttribute("userDto") UserCreationDto userDto,
//            RedirectAttributes redirectAttributes,
//            HttpServletRequest request) {
//        userDto.addBlankPhone();
//        redirectAttributes.addFlashAttribute("userDto", userDto);
//        return new RedirectView(request.getHeader("referer"), true);
//    }
//
//    @GetMapping("/user/create")
//    public String userSaveSeparate(
//            Model model,
//            @ModelAttribute("userDto") UserCreationDto userDto) {
//        model.addAttribute("userDto", userDto);
//        return "userCreate.html";
//    }
//
//    @ModelAttribute("userDto")
//    public UserCreationDto userDto() {
//        return new UserCreationDto();
//    }
//
//}
