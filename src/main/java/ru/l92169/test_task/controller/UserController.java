package ru.l92169.test_task.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.l92169.test_task.entity.User;
import ru.l92169.test_task.service.UserServiceImpl;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping("")
    public String getUserHome() {
        return "index";
    }


    @GetMapping("/get_age")
    public String getAge(Model model, @RequestParam(required = false, name = "name") String name) {
        if (name == null || name.isEmpty()) {
            String messageError = "Введите имя";
            model.addAttribute("exception", messageError);
            return "user/get_age";
        } else {
            name = name.trim();
            List<User> users = userService.getUsers(name);
            if (users.size() == 0) {
                users = userService.workWithExternalService(name);
            }
            model.addAttribute("users", users);
        }
        return "user/get_age";
    }

    @GetMapping("/statistics")
    public String getStatistics(Model model) {
        model.addAttribute("frequencyFromFile", userService.getFrequencyFromFile(true));
        model.addAttribute("frequencyNotFromFile", userService.getFrequencyFromFile(false));
        model.addAttribute("maxAgeFromFile", userService.findNamesWithMaxAge(true));
        model.addAttribute("maxAgeNotFromFile", userService.findNamesWithMaxAge(false));
        return "user/statistics";
    }
}