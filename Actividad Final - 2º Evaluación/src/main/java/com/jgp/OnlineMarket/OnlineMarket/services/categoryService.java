package com.jgp.OnlineMarket.OnlineMarket.services;

import com.jgp.OnlineMarket.OnlineMarket.models.dao.ICategoryEntityDAO;
import com.jgp.OnlineMarket.OnlineMarket.models.entities.CategoryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class categoryService {
    private final ICategoryEntityDAO categoryDAO;

    public categoryService(ICategoryEntityDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    public List<CategoryEntity> getAllCategories() {
        return (List<CategoryEntity>) categoryDAO.findAll();
    }

    public CategoryEntity getCategoryById(int categoryId) {
        return categoryDAO.findById(categoryId).orElse(null);
    }
}
