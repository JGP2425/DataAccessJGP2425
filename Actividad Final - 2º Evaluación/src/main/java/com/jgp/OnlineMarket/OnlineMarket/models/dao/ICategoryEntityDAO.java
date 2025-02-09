package com.jgp.OnlineMarket.OnlineMarket.models.dao;

import com.jgp.OnlineMarket.OnlineMarket.models.entities.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryEntityDAO extends CrudRepository<CategoryEntity, Integer> {
}
