package com.jgp.OnlineMarket.OnlineMarket.controllers;

import com.jgp.OnlineMarket.OnlineMarket.models.entities.CategoryEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.ProductEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.dto.ProductForm;
import com.jgp.OnlineMarket.OnlineMarket.services.categoryService;
import com.jgp.OnlineMarket.OnlineMarket.services.productService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import com.jgp.OnlineMarket.OnlineMarket.services.sellersService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/view")
public class viewController {
    private sellersService sellersService;
    private categoryService categoryService;
    private productService productService;

    public viewController (sellersService sellersService, categoryService categoryService, productService productService) {
        this.sellersService = sellersService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @GetMapping("/index")
    public String index(@AuthenticationPrincipal User user, Model model) {
        SellerEntity seller = sellersService.findSellerByCif(user.getUsername());
        model.addAttribute("seller", seller);
        model.addAttribute("passwordMismatch",false);
        return "index";
    }

    @GetMapping("/addProduct")
    public String addProductView(@RequestParam(value = "categoryId", required = false) Integer categoryId, @AuthenticationPrincipal User user, Model model) {
        SellerEntity seller = sellersService.findSellerByCif(user.getUsername());

        List<CategoryEntity> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        ProductForm productForm = new ProductForm();
        if (categoryId != null) {
            productForm.setCategoryId(categoryId);
        }
        model.addAttribute("productForm", productForm);

        if (categoryId != null) {
            List<ProductEntity> products = productService.getProductsByCategory(categoryId);

            List<ProductEntity> availableProducts = products.stream()
                    .filter(product -> seller.getSellerProducts().stream()
                            .noneMatch(sellerProduct -> sellerProduct.getProduct().getProductId() == product.getProductId()))
                    .toList();

            model.addAttribute("products", availableProducts);
            model.addAttribute("hasProducts", !availableProducts.isEmpty());
        }
        else {
            model.addAttribute("products", List.of());
            model.addAttribute("hasProducts", false);
        }

        model.addAttribute("selectedCategoryId", categoryId);

        return "addProduct";
    }
}
