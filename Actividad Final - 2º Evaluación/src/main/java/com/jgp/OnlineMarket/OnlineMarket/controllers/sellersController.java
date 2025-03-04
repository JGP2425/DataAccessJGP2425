package com.jgp.OnlineMarket.OnlineMarket.controllers;

import com.jgp.OnlineMarket.OnlineMarket.models.dto.SellerDTO;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.ProductEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerProductEntity;
import com.jgp.OnlineMarket.OnlineMarket.services.productService;
import com.jgp.OnlineMarket.OnlineMarket.services.sellersService;
import io.swagger.v3.core.util.Json;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.coyote.Response;
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
import java.util.ArrayList;
import java.util.List;

@Tag(name = "Sellers", description = "API for managing sellers")
@RestController
@Controller
@RequestMapping("/sellers")
public class sellersController {
    @Autowired
    private sellersService sellersService;

    @Autowired
    private productService productService;


    sellersController(sellersService sellersService, productService productService) {
        this.sellersService = sellersService;
        this.productService = productService;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @Operation(summary = "Retrieve all sellers", description = "Fetches a list of all registered sellers")
    @GetMapping
    public List<SellerEntity> findAllSeller() {
        return sellersService.findAllSellers();
    }

    @Operation(summary = "Get seller by CIF", description = "Fetches seller details based on their CIF")
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

    @Operation(summary = "Update a seller", description = "Updates seller data in the database")
    @PostMapping("/updateSeller")
    public String updateSeller(@ModelAttribute SellerEntity seller) {
        sellersService.updateSeller(seller);
        return "redirect:/view/index";
    }

    @Operation(summary = "Find sellers by product", description = "Find sellers that offer the product")
    @GetMapping("/findSellersByProducts/{productId}")
    public List<SellerDTO> findSellerByProducts(@PathVariable Integer productId) {
        ProductEntity product = productService.getProductById(productId);
        List<SellerProductEntity> listSellers = productService.getSellersByProduct(product);
        List<SellerDTO> listSellersDTO = new ArrayList<>();
        for (int i = 0; i < listSellers.size(); i++) {
            SellerDTO seller = new SellerDTO();
            SellerProductEntity sellerProduct = listSellers.get(i);
            seller.setId(sellerProduct.getSeller().getSellerId());
            seller.setCif(sellerProduct.getSeller().getCif());
            seller.setName(sellerProduct.getSeller().getName());
            seller.setBusinessName(sellerProduct.getSeller().getBusinessName());
            seller.setEmail(sellerProduct.getSeller().getEmail());
            seller.setPhone(sellerProduct.getSeller().getPhone());

            listSellersDTO.add(seller);
        }

        return listSellersDTO;
    }
}
