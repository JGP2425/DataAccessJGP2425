package org.jgp2425.unit.finalactivity_v1.entities;

import org.hibernate.Session;
import org.jgp2425.unit.finalactivity_v1.Utils;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "sellers")
public class Seller {

    @Id
    @Column(name = "seller_id")
    private int sellerId;

    @Column(name = "cif")
    private String cif;

    @Column(name = "name")
    private String name;

    @Column(name = "business_name")
    private String businessName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "plain_password")
    private String plainPassword;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<SellerProduct> sellerProducts;

    // Getters y setters
    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Product> getSellerProducts() {
        ArrayList<Product> products = new ArrayList<>();
        for(SellerProduct sellerProduct: this.sellerProducts)
            products.add(sellerProduct.getProduct());

        return products;

    }

    public void setSellerProducts(List<SellerProduct> sellerProducts) {
        this.sellerProducts = sellerProducts;
    }

    public Seller getSellerByCif(Session session, String cif) {
        return session.createQuery("from Seller where cif= :cif", Seller.class).setParameter("cif", cif).uniqueResult();
    }

    public boolean validateSeller(String password) {
        return Utils.validatePassword(password, this.password);
    }
}