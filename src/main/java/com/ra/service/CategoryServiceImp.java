package com.ra.service;

import com.ra.model.entity.Category;
import com.ra.model.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        List<Category> list = categoryRepository.findAll();// findAll() có sẵn rồi chỉ cần gọi và sử dụng
        return list;
    }

    @Override
    public Boolean save(Category category) {
        categoryRepository.save(category);
        return true;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
