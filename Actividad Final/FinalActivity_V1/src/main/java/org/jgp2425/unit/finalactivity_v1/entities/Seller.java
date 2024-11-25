package org.jgp2425.unit.finalactivity_v1.entities;

import net.bytebuddy.asm.Advice;
import org.hibernate.Session;
import org.hibernate.event.spi.PreUpdateEvent;
import org.jgp2425.unit.finalactivity_v1.Utils;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "url")
    private String url;

    @Column(name = "pro")
    private boolean pro;

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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean getPro() {
        return pro;
    }

    public void setPro(boolean pro) {
        this.pro = pro;
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

    //Method to return the count of offers in a period of time
    public int countOffersByPeriodOfTime(Session session, LocalDate fromDate, LocalDate toDate) {
        return  session
                .createQuery("from SellerProduct where offer_start_date <= :fromDate and offer_end_date >= :toDate", SellerProduct.class)
                .setParameter("fromDate", fromDate)
                .setParameter("toDate", toDate)
                .getResultList().size();
    }
}