package com.jgp.OnlineMarket.OnlineMarket.models.dao;

import com.jgp.OnlineMarket.OnlineMarket.models.entities.CategoryEntity;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.ProductEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductEntityDAO extends CrudRepository<ProductEntity, Integer> {
    List<ProductEntity> findByCategory(CategoryEntity categoryEntity);
}
