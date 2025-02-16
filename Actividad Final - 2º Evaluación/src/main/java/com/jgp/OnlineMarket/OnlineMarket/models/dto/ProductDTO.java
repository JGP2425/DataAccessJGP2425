package com.jgp.OnlineMarket.OnlineMarket.models.dto;

import java.math.BigDecimal;

public class ProductDTO {
    private Integer productId;
    private String productName;
    private BigDecimal productPrice;

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
