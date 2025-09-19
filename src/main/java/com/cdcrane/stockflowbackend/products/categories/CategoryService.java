package com.cdcrane.stockflowbackend.products.categories;

import com.cdcrane.stockflowbackend.config.exceptions.ResourceNotFoundException;
import com.cdcrane.stockflowbackend.products.categories.dto.CreateCategoryDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryService implements CategoryUseCase{

    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Page<Category> getAllCategories(Pageable pageable) {

        Page<Category> categories = categoryRepo.findAll(pageable);

        if (categories.isEmpty()) {
            throw new ResourceNotFoundException("No categories found");
        }

        return categories;

    }

    @Override
    public Category getCategoryById(UUID categoryId) {

        return categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + categoryId + " not found"));

    }

    @Override
    @Transactional
    public void createCategory(CreateCategoryDTO category) {

        Category newCategory = Category.builder()
                .name(category.categoryName())
                .build();

        categoryRepo.save(newCategory);

    }

    @Override
    public Category updateCategory(UUID categoryId, String categoryName) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category with ID " + categoryId + " not found"));

        category.setName(categoryName);

        return categoryRepo.save(category);

    }
}
