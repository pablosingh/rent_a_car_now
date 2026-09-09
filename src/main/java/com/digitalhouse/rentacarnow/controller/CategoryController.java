package com.digitalhouse.rentacarnow.controller;

import com.digitalhouse.rentacarnow.dto.ApiResponse;
import com.digitalhouse.rentacarnow.entity.Category;
import com.digitalhouse.rentacarnow.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ApiResponse<List<Category>> findAll() {
        return ApiResponse.success(categoryService.findAll());
    }

    @PostMapping
    public ApiResponse<Category> create(@RequestParam String name,
                                        @RequestParam(value = "description", required = false) String description) {
        return ApiResponse.success(categoryService.create(name, description));
    }

    @PutMapping("/{id}")
    public ApiResponse<Category> update(@PathVariable Long id,
                                        @RequestParam String name,
                                        @RequestParam(value = "description", required = false) String description) {
        return ApiResponse.success(categoryService.update(id, name, description));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResponse.success(null);
    }
}
