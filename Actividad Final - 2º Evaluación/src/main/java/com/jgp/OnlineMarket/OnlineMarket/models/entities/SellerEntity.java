package com.jgp.OnlineMarket.OnlineMarket.models.entities;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sellers")
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Auto-incremental
    @Column(name = "seller_id")
    private int sellerId;

    @NotEmpty(message = "CIF cannot be empty")
    @Size(max = 20, message = "CIF cannot exceed 20 characters")
    @Column(name = "cif", unique = true, nullable = false)
    private String cif;

    @NotEmpty(message = "Name cannot be empty")
    @Size(max = 100, message = "Name cannot exceed 100 characters")
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 100, message = "Business name cannot exceed 100 characters")
    @Column(name = "business_name")
    private String businessName;

    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$", message = "Phone must contain 9 digits with a hyphen separating every three digits")
    @Column(name = "phone")
    private String phone;

    @Email(message = "Must be a valid email address")
    @Size(max = 90, message = "Email cannot exceed 90 characters")
    @Column(name = "email", nullable = true)
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 50, message = "Plain password must be between 6 and 50 characters")
    @Column(name = "plain_password", nullable = false)
    private String plainPassword;

    @NotEmpty(message = "Password cannot be empty")
    @Size(min = 6, max = 100, message = "Password must be between 6 and 100 characters")
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SellerProductEntity> sellerProducts = new ArrayList<>();

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

    public List<SellerProductEntity> getSellerProducts() {
        return sellerProducts;
    }

    public void setSellerProducts(List<SellerProductEntity> sellerProducts) {
        this.sellerProducts = sellerProducts;
    }

    public boolean checkIfSameEntity(SellerEntity otherSeller) {
        if (this.cif.equals(otherSeller.getCif()) && this.email.equals(otherSeller.getEmail())  && this.phone.equals(otherSeller.getPhone()) && this.plainPassword.equals(otherSeller.getPlainPassword()) && this.businessName.equals(otherSeller.getBusinessName()) && this.name.equals(otherSeller.getName()))
            return true;
        else
            return false;
    }


}
