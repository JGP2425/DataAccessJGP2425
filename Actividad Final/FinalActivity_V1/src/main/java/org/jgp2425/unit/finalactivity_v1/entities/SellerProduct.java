package org.jgp2425.unit.finalactivity_v1.entities;

import org.hibernate.Session;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seller_products")
public class SellerProduct {

    @Id
    @Column(name = "seller_product_id")
    private int sellerProductId;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id")
    private Seller seller;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "price")
    private double price;

    @Column(name = "offer_price", nullable = true)
    private Double offerPrice = 0.0;

    @Column(name = "offer_start_date", nullable = true)
    private LocalDate offerStartDate;

    @Column(name = "offer_end_date", nullable = true)
    private LocalDate offerEndDate;

    @Column(name = "stock")
    private int stock;

    // Getters y setters
    public int getSellerProductId() {
        return sellerProductId;
    }

    public void setSellerProductId(int sellerProductId) {
        this.sellerProductId = sellerProductId;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(double offerPrice) {
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

    public SellerProduct getSellerProduct(Session session, int sellerId, int productID) {
        return session.createQuery("from SellerProduct where seller_id= :sellerId and product_id= :productId", SellerProduct.class)
                        .setParameter("sellerId", sellerId)
                        .setParameter("productId", productID)
                        .uniqueResult();
    }
}
