package com.jgp.OnlineMarket.OnlineMarket.controllers;

import com.jgp.OnlineMarket.OnlineMarket.models.dto.LoginForm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Tag(name = "Authentication", description = "API for user login management")
@Controller
public class loginController {

    @Operation(summary = "Show login page", description = "Displays the login page where users can enter their credentials.")
    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login";
    }

    @Operation(summary = "Process login", description = "Validates user credentials and redirects to the homepage upon successful authentication.")
    @PostMapping("/login")
    public String processLogin(@Valid LoginForm loginForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("loginForm", loginForm);
            return "login";
        }

        return "redirect:/view/index";
    }
}
