package com.jgp.OnlineMarket.OnlineMarket.services;

import com.jgp.OnlineMarket.OnlineMarket.models.dao.IProductEntityDAO;
import com.jgp.OnlineMarket.OnlineMarket.models.dao.ISellerProductEntityDAO;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.CategoryEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.ProductEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerProductEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class productService {
    private final IProductEntityDAO productDAO;
    private final ISellerProductEntityDAO sellerProductDAO;
    private final categoryService categoryService;

    public productService(IProductEntityDAO productDAO, ISellerProductEntityDAO sellerProductDAO, categoryService categoryService) {
        this.productDAO = productDAO;
        this.sellerProductDAO = sellerProductDAO;
        this.categoryService = categoryService;
    }

    public List<ProductEntity> getProductsByCategory(int categoryId) {
        CategoryEntity category = categoryService.getCategoryById(categoryId);
        if (category == null) {
            throw new IllegalArgumentException("Category not found for ID: " + categoryId);
        }
        return productDAO.findByCategory(category);
    }

    public ProductEntity getProductById(int productId) {
        return  productDAO.findById(productId).orElse(null);
    }

    public void addProductToSeller(SellerEntity seller, ProductEntity product, BigDecimal price, int stock) {
        boolean productExists = seller.getSellerProducts().stream()
                .anyMatch(sellerProduct -> sellerProduct.getProduct().getProductId() == product.getProductId());

        if (productExists) {
            throw  new IllegalArgumentException("Product is already associated with the seller");
        }

        SellerProductEntity sellerProduct = new SellerProductEntity();
        sellerProduct.setSeller(seller);
        sellerProduct.setProduct(product);
        sellerProduct.setPrice(price);
        sellerProduct.setStock(stock);

        sellerProductDAO.save(sellerProduct);

    }
}
