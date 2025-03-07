package com.jgp.OnlineMarket.OnlineMarket.controllers;

import com.jgp.OnlineMarket.OnlineMarket.models.dto.OfferForm;
import com.jgp.OnlineMarket.OnlineMarket.models.dto.ProductDTO;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.CategoryEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.ProductEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.dto.ProductForm;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerProductEntity;
import com.jgp.OnlineMarket.OnlineMarket.services.categoryService;
import com.jgp.OnlineMarket.OnlineMarket.services.productService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.jgp.OnlineMarket.OnlineMarket.services.sellersService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;

@Tag(name = "View", description = "Endpoints for managing seller views and operations")
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

    @Operation(summary = "Seller dashboard", description = "Displays the seller's dashboard with relevant information.")
    @GetMapping("/index")
    public String index(@AuthenticationPrincipal User user, Model model) {
        SellerEntity seller = sellersService.findSellerByCif(user.getUsername());
        model.addAttribute("seller", seller);
        model.addAttribute("passwordMismatch",false);
        return "index";
    }

    @Operation(summary = "Update seller", description = "Updates the seller's information in the database.")
    @PostMapping("/updateSeller")
    public String updateSeller(@Valid @ModelAttribute("seller") SellerEntity seller, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model, @AuthenticationPrincipal User user) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("seller", seller);
            return "index";
        }

        seller.setCif(user.getUsername());

        if (!seller.getPlainPassword().equals(seller.getPassword()))
        {
            model.addAttribute("notMatchingPass", "Passwords do not match");
            model.addAttribute("passwordMismatch", true);
            model.addAttribute("seller",seller);
            return "index";
        }
        else {
            SellerEntity sellerBBDD = sellersService.findSellerByCif(seller.getCif());
            if (seller.checkIfSameEntity(sellerBBDD))
            {
                model.addAttribute("seller", seller);
                redirectAttributes.addFlashAttribute("warningMessage", "No changes detected. No update was made.");
            }
            else {
                SellerEntity sellerUpdated = sellersService.updateSeller(seller);
                model.addAttribute("seller", sellerUpdated);
                redirectAttributes.addFlashAttribute("okMessage", "Seller data updated successfully!");
            }
        }

        return "redirect:/view/index";
    }

    @Operation(summary = "Add product view", description = "Displays the page to add a new product for the seller.")
    @GetMapping("/addProductView")
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

    @Operation(summary = "Add product", description = "Adds a new product to the seller's inventory.")
    @PostMapping("/addProduct")
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

        return "redirect:/view/addProductView";
    }

    @Operation(summary = "Add offer view", description = "Displays the page to add a special offer for a product.")
    @GetMapping("/addOfferView")
    public String addOfferView(@RequestParam(value = "productId", required = false) Integer productId, @AuthenticationPrincipal User user, Model model) {
        SellerEntity seller = sellersService.findSellerByCif(user.getUsername());

        List<ProductDTO> products = productService.getProductsBySeller(seller, false);
        products.sort(Comparator.comparing(ProductDTO::getProductName));

        OfferForm offerForm = new OfferForm();
        if (productId != null) {
            ProductEntity productSelected = productService.getProductById(productId);
            SellerProductEntity offer = productService.getOfferByProduct(productSelected, seller);

            if (offer.getOfferPrice() != null && offer.getOfferStartDate() != null && offer.getOfferEndDate() != null) {
                offerForm.setFrom(offer.getOfferStartDate());
                offerForm.setTo(offer.getOfferEndDate());
                offerForm.setOfferPrice(offer.getOfferPrice());
                offerForm.setDiscount(offer.getPrice().subtract(offer.getOfferPrice()).divide(offer.getPrice(), RoundingMode.HALF_UP).multiply(new BigDecimal("100")).setScale(2, BigDecimal.ROUND_HALF_UP));
            }

            offerForm.setProductId(productId);
        }

        model.addAttribute("products", products);
        model.addAttribute("offerForm", offerForm);
        model.addAttribute("productIdSelected", productId);

        return "addOffer";
    }

    @Operation(summary = "Add offer", description = "Adds a discount offer to a seller's product.")
    @PostMapping("/addOffer")
    public String addOfferToSeller(@ModelAttribute("offerForm") @Valid OfferForm offerForm, BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes, @AuthenticationPrincipal User user, Model model)
    {
        SellerEntity seller = sellersService.findSellerByCif(user.getUsername());

        if (bindingResult.hasErrors()) {

            List<ProductDTO> products = productService.getProductsBySeller(seller, false);
            products.sort(Comparator.comparing(ProductDTO::getProductName));

            model.addAttribute("products", products);
            model.addAttribute("offerForm", offerForm);
            model.addAttribute("productIdSelected", offerForm.getProductId());

            return "addOffer";
        }

        if (productService.isOfferOverlapping(seller, offerForm.getFrom(), offerForm.getTo())) {
            redirectAttributes.addFlashAttribute("warningMessage", "There is already an offer in this date range.");
            return "redirect:/view/addOfferView";
        }

        try {

            SellerProductEntity offer = productService.getOfferByProduct(productService.getProductById(offerForm.getProductId()), seller);
            offer.setOfferPrice(offerForm.getDiscount().divide(new BigDecimal(100)).multiply(offer.getPrice()));
            offer.setOfferStartDate(offerForm.getFrom());
            offer.setOfferEndDate(offerForm.getTo());

            productService.addOffer(offer);
            redirectAttributes.addFlashAttribute("okMessage", "Offer Added sucessfully");
        }
        catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("warningMessage", e.getMessage());
        }

        return "redirect:/view/addOfferView";
    }

    @Operation(summary = "Show Products view", description = "Displays the page to show the products of the seller.")
    @GetMapping("/showProductsView")
    public String showProductsView(@AuthenticationPrincipal User user, Model model) {
        SellerEntity seller = sellersService.findSellerByCif(user.getUsername());

        List<ProductDTO> products = productService.getProductsBySeller(seller, false);
        products.sort(Comparator.comparing(ProductDTO::getProductName));

        model.addAttribute("products", products);

        return "showProducts";
    }

    @Operation(summary = "Show Products view", description = "Displays the page to show the products of the seller.")
    @GetMapping("/showProductsWithOfferView")
    public String showProductsWithOfferView(@AuthenticationPrincipal User user, Model model) {
        SellerEntity seller = sellersService.findSellerByCif(user.getUsername());

        List<ProductDTO> products = productService.getProductsBySeller(seller,true);
        products.sort(Comparator.comparing(ProductDTO::getProductName));


        model.addAttribute("products", products);

        return "showProductsWithOffer";
    }
}
