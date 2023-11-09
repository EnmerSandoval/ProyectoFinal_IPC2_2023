package com.example.backendguateempleos.controller;

import com.example.backendguateempleos.model.Category;
import com.example.backendguateempleos.querys.QueryCategories;
import java.util.List;
import java.util.Optional;

public class CategoryService {
    private final QueryCategories queryCategories = new QueryCategories();

    public List<Category> getAllCategories(){
        return queryCategories.getAllCategories();
    }

    public boolean insertNewCategory(Category category){
        return queryCategories.insertCategory(category);
    }

    public boolean updateCategory(Category category){
        return queryCategories.updateCategory(category);
    }

    public Optional<Category> getCategory(Category category){
        return queryCategories.getCategoryByNumberCategory(category);
    }

    public int maxNumberCategory(){
        return queryCategories.maxCategory();
    }
}
