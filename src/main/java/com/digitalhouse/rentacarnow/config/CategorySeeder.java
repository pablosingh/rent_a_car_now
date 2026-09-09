package com.digitalhouse.rentacarnow.config;

import com.digitalhouse.rentacarnow.entity.Category;
import com.digitalhouse.rentacarnow.repository.CategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategorySeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public CategorySeeder(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0) {
            return;
        }
        List<String> defaults = List.of(
                "Económico", "Compacto", "Mediano", "SUV", "Pickup",
                "Familiar", "Premium", "Utilitario", "Eléctrico"
        );
        for (String name : defaults) {
            Category c = new Category();
            c.setName(name);
            categoryRepository.save(c);
        }
    }
}
