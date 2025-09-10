package com.cdcrane.stockflowbackend.products.categories;

import com.cdcrane.stockflowbackend.config.exceptions.ResourceNotFoundException;
import com.cdcrane.stockflowbackend.products.categories.dto.CreateCategoryDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    @Transactional
    public void createCategory(CreateCategoryDTO category) {

        Category newCategory = Category.builder()
                .name(category.categoryName())
                .build();

        categoryRepo.save(newCategory);

    }
}
