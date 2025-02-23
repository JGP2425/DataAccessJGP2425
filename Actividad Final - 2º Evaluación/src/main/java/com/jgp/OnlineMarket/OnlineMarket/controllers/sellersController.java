package com.jgp.OnlineMarket.OnlineMarket.controllers;

import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.services.sellersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import javax.persistence.EntityNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/sellers")
public class sellersController {
    @Autowired
    private sellersService sellersService;


    sellersController(sellersService sellersService) {
        this.sellersService = sellersService;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @GetMapping
    public List<SellerEntity> findAllSeller() {
        return sellersService.findAllSellers();
    }

    @GetMapping("/cif/{cif}")
    public ResponseEntity<SellerEntity> getSellerByCif(@PathVariable String cif) {
        SellerEntity seller = sellersService.findSellerByCif(cif);

        if (seller != null) {

            return ResponseEntity.ok(seller);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/updateSeller")
    public String updateSeller(@ModelAttribute SellerEntity seller) {
        sellersService.updateSeller(seller);
        return "redirect:/view/index";
    }

}
