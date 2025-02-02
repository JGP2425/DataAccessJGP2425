package com.jgp.OnlineMarket.OnlineMarket.services;

import com.jgp.OnlineMarket.OnlineMarket.models.dao.ISellerEntityDAO;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.utils.Utils;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class sellersService {
    private final ISellerEntityDAO sellerDAO;

    public sellersService(ISellerEntityDAO sellerDAO) {
        this.sellerDAO = sellerDAO;
    }

    public List<SellerEntity> findAllSellers() {
        return (List<SellerEntity>) sellerDAO.findAll();

    }

    public SellerEntity findSellerByCif(String cif) {
        return sellerDAO.findByCif(cif);
    }

    public SellerEntity updateSeller(SellerEntity seller) {
        SellerEntity sellerBBDD = sellerDAO.findByCif(seller.getCif());
        sellerBBDD.setName(seller.getName());
        sellerBBDD.setBusinessName(seller.getBusinessName());
        sellerBBDD.setEmail(seller.getEmail());
        sellerBBDD.setPhone(seller.getPhone());
        String passwordConverted = Utils.MD5Converter(seller.getPlainPassword());
        sellerBBDD.setPassword(passwordConverted.toUpperCase());
        sellerBBDD.setPlainPassword(seller.getPlainPassword());

        return sellerDAO.save(sellerBBDD);
    }

}
