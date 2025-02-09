package com.jgp.OnlineMarket.OnlineMarket.models.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "seller_products",
        uniqueConstraints = @UniqueConstraint(columnNames = {"seller_id", "product_id"}))
public class SellerProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seller_product_id")
    private int sellerProductId;

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "seller_id", nullable = false)
    private SellerEntity seller;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    private ProductEntity product;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than 0")
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @DecimalMin(value = "0.0", inclusive = true, message = "Offer price must be at least 0")
    @Column(name = "offer_price")
    private BigDecimal offerPrice;

    @Column(name = "offer_start_date")
    private LocalDate offerStartDate;

    @Column(name = "offer_end_date")
    private LocalDate offerEndDate;

    @NotNull(message = "Stock cannot be null")
    @Min(value = 1, message = "Stock must be at least 0")
    @Column(name = "stock", nullable = false)
    private int stock = 0;

    // Getters y setters
    public int getSellerProductId() {
        return sellerProductId;
    }

    public void setSellerProductId(int sellerProductId) {
        this.sellerProductId = sellerProductId;
    }

    public SellerEntity getSeller() {
        return seller;
    }

    public void setSeller(SellerEntity seller) {
        this.seller = seller;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public LocalDate getOfferStartDate() {
        return offerStartDate;
    }

    public void setOfferStartDate(LocalDate offerStartDate) {
        this.offerStartDate = offerStartDate;
    }

    public LocalDate getOfferEndDate() {
        return offerEndDate;
    }

    public void setOfferEndDate(LocalDate offerEndDate) {
        this.offerEndDate = offerEndDate;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }
}
