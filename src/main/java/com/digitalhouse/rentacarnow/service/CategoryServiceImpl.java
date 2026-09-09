package com.digitalhouse.rentacarnow.service;

import com.digitalhouse.rentacarnow.entity.Category;
import com.digitalhouse.rentacarnow.exception.ConflictException;
import com.digitalhouse.rentacarnow.repository.CarRepository;
import com.digitalhouse.rentacarnow.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CarRepository carRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CarRepository carRepository) {
        this.categoryRepository = categoryRepository;
        this.carRepository = carRepository;
    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category create(String name, String description) {
        if (categoryRepository.existsByName(name)) {
            throw new ConflictException("Ya existe una categoría con el nombre: " + name);
        }
        Category category = new Category();
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, String name, String description) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        if (!category.getName().equals(name) && categoryRepository.existsByName(name)) {
            throw new ConflictException("Ya existe una categoría con el nombre: " + name);
        }
        category.setName(name);
        category.setDescription(description);
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        if (carRepository.existsByCategory(category.getName())) {
            throw new ConflictException("No se puede borrar la categoría '" + category.getName() + "' porque hay autos que la usan.");
        }
        categoryRepository.deleteById(id);
    }
}
