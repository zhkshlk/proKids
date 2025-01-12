package com.example.prokids.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainPageController {

    @GetMapping("/")
    public String showMenu(Model model) {
        String content = "доказательство что главная страница открывается";
        if (content == null) {
            model.addAttribute("error", "Текст не найден!");
        } else {
            model.addAttribute("content", content);
        }
        model.addAttribute("games", "лишний текст");
        return "main_page";


    }
}
