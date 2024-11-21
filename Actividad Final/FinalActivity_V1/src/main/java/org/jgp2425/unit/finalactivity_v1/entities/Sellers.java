package org.jgp2425.unit.finalactivity_v1.entities;

import org.hibernate.Session;
import org.hibernate.procedure.internal.Util;
import org.jgp2425.unit.finalactivity_v1.Utils;

import javax.persistence.*;

@Entity
@Table(name = "sellers")
public class Sellers {

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

    @Column(name = "email")
    private String email;

    @Column(name = "plain_password")
    private String plainPassword;

    @Column(name = "password")
    private String password;

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

    public Sellers getSellerByCif(Session session, String cif) {
        return session.createQuery("from Sellers where cif= :cif", Sellers.class).setParameter("cif", cif).uniqueResult();
    }

    public boolean validateSeller(String password) {
        return Utils.validatePassword(password, this.password);
    }
}