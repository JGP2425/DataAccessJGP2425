package com.jgp.OnlineMarket.OnlineMarket.controllers;

import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.jgp.OnlineMarket.OnlineMarket.services.sellersService;

@Controller
public class viewController {
    private sellersService sellersService;

    public viewController (sellersService sellersService) {
        this.sellersService = sellersService;
    }

    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user, Model model) {
        SellerEntity seller = sellersService.findSellerByCif(user.getUsername());
        model.addAttribute("seller", seller);
        model.addAttribute("passwordMismatch",false);
        return "index";
    }
}
