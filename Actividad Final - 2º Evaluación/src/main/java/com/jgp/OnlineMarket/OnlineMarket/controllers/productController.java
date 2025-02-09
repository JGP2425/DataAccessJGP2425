package com.jgp.OnlineMarket.OnlineMarket.controllers;

import com.jgp.OnlineMarket.OnlineMarket.models.dto.ProductForm;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.CategoryEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.services.productService;
import com.jgp.OnlineMarket.OnlineMarket.services.sellersService;
import com.jgp.OnlineMarket.OnlineMarket.services.categoryService;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.ProductEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/product")
public class productController {
    @Autowired
    private productService productService;

    @Autowired
    private sellersService sellersService;

    @Autowired
    private categoryService categoryService;

    public productController(productService productService, sellersService sellerService, categoryService categoryService) {
        this.productService = productService;
        this.sellersService = sellerService;
        this.categoryService = categoryService;
    }

    @PostMapping("/add")
    public String addProductToSeller(@ModelAttribute("productForm") @Valid ProductForm productForm,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes,
                                     @AuthenticationPrincipal User user, Model model)
    {
        SellerEntity seller = sellersService.findSellerByCif(user.getUsername());

        if (bindingResult.hasErrors()) {

            List<CategoryEntity> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);

            if (productForm.getCategoryId() != null) {
                List<ProductEntity> products = productService.getProductsByCategory(productForm.getCategoryId());
                List<ProductEntity> availableProducts = products.stream()
                        .filter(product -> seller.getSellerProducts().stream()
                                .noneMatch(sellerProduct -> sellerProduct.getProduct().getProductId() == product.getProductId()))
                        .toList();

                model.addAttribute("products", availableProducts);
                model.addAttribute("hasProducts", !availableProducts.isEmpty());
            } else {
                model.addAttribute("products", List.of());
                model.addAttribute("hasProducts", false);
            }

            model.addAttribute("selectedCategoryId", productForm.getCategoryId());
            model.addAttribute("productForm", productForm);
            model.addAttribute("productId", productForm.getProductId());

            return "addProduct";
        }

        ProductEntity product = productService.getProductById(productForm.getProductId());

        try {
            productService.addProductToSeller(seller,product,productForm.getPrice(),productForm.getStock());
            redirectAttributes.addFlashAttribute("okMessage", "Product Added sucessfully");
        }
        catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("warningMessage", e.getMessage());
        }

        return "redirect:/view/addProduct";
    }

}
