package com.ra.service;

import com.ra.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();// lấy tất cả dữ liệu

    Boolean save(Category category);

    Category findById(Long id);

    void delete(Long id);
}
