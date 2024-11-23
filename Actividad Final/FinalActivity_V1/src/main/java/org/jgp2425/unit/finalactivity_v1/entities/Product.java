package org.jgp2425.unit.finalactivity_v1.entities;

import org.hibernate.Session;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "active", nullable = false)
    private boolean active;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<SellerProduct> sellerProducts;

    // Getters y setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Set<SellerProduct> getSellerProducts() {
        return sellerProducts;
    }

    public void setSellerProducts(Set<SellerProduct> sellerProducts) {
        this.sellerProducts = sellerProducts;
    }

    //TODO: Ver de eliminarlo si no me hace falta.
    public Product getProductByName(Session session, String productName) {
        return session.createQuery("from Product where productName = :productName", Product.class)
                .setParameter("productName", productName)
                .uniqueResult();
    }
}
