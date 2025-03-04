package com.jgp.OnlineMarket.OnlineMarket.services;

import com.jgp.OnlineMarket.OnlineMarket.models.dao.IProductEntityDAO;
import com.jgp.OnlineMarket.OnlineMarket.models.dao.ISellerProductEntityDAO;
import com.jgp.OnlineMarket.OnlineMarket.models.dto.ProductDTO;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.CategoryEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.ProductEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerProductEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<ProductDTO> getProductsBySeller(SellerEntity seller, boolean withOffer) {
        List<SellerProductEntity> sellerProductList = sellerProductDAO.getSellerProductsBySeller(seller);
        List<ProductDTO> productDTOList = new ArrayList<>();

        if (!withOffer) {
            for (int i = 0; sellerProductList.size() > i; i++) {
                ProductEntity product = sellerProductList.get(i).getProduct();
                ProductDTO productDTO = new ProductDTO();

                productDTO.setProductId(product.getProductId());
                productDTO.setProductName(product.getProductName());
                productDTO.setProductPrice(sellerProductList.get(i).getPrice());
                productDTO.setCategoryName(product.getCategory().getCategoryName());

                productDTOList.add(productDTO);
            }
        }
        else {
            LocalDate today = LocalDate.now();
            for (int i = 0; i < sellerProductList.size(); i++) {
                SellerProductEntity sellerProduct = sellerProductList.get(i);
                if (sellerProduct.getOfferStartDate() != null && sellerProduct.getOfferEndDate() != null) {
                    if (today.isAfter(sellerProduct.getOfferStartDate()) || today.isEqual(sellerProduct.getOfferStartDate())) {
                        ProductEntity product = sellerProductList.get(i).getProduct();
                        ProductDTO productDTO = new ProductDTO();

                        productDTO.setProductId(product.getProductId());
                        productDTO.setProductName(product.getProductName());
                        productDTO.setProductPrice(sellerProductList.get(i).getOfferPrice());
                        productDTO.setCategoryName(product.getCategory().getCategoryName());

                        productDTOList.add(productDTO);
                    }
                }
            }
        }

        return productDTOList;
    }

    public SellerProductEntity getOfferByProduct(ProductEntity product, SellerEntity seller) {
        return sellerProductDAO.getSellerProductByProductAndSeller(product, seller);
    }

    public List<SellerProductEntity> getSellersByProduct(ProductEntity product) {
        return sellerProductDAO.getSellersByProduct(product);
    }

    public void addOffer(SellerProductEntity offer) {
        sellerProductDAO.save(offer);
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

    public boolean isOfferOverlapping(SellerEntity seller, LocalDate startDate, LocalDate endDate) {
        List<SellerProductEntity> overlappingOffers = sellerProductDAO.findOverlappingOffers(seller, startDate, endDate);
        return !overlappingOffers.isEmpty();
    }

}
