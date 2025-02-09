package com.jgp.OnlineMarket.OnlineMarket.models.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ProductForm {
    @NotNull(message = "Category is required")
    private Integer categoryId;
    @NotNull(message = "Product is required")
    private Integer productId;
    @Positive(message = "Stock must be greater than 0")
    private Integer stock = 0;
    @Positive(message = "Price must be greater than 0")
    private BigDecimal price;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
