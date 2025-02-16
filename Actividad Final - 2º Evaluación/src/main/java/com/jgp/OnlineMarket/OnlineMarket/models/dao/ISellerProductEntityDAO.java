package com.jgp.OnlineMarket.OnlineMarket.models.dao;

import com.jgp.OnlineMarket.OnlineMarket.models.entities.ProductEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISellerProductEntityDAO extends CrudRepository<SellerProductEntity, Integer> {
    List<SellerProductEntity> getSellerProductsBySeller(SellerEntity seller);
    SellerProductEntity getSellerProductByProductAndSeller(ProductEntity product, SellerEntity seller);
}
