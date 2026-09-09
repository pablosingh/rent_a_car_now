package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();

    Category create(String name, String description);

    Category update(Long id, String name, String description);

    void delete(Long id);
}
