package com.jgp.OnlineMarket.OnlineMarket.models.dao;

import com.jgp.OnlineMarket.OnlineMarket.models.entities.SellerProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISellerProductEntityDAO extends CrudRepository<SellerProductEntity, Integer> {

}
