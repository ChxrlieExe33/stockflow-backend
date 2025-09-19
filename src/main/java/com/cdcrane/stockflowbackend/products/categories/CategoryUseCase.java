package com.cdcrane.stockflowbackend.products.categories;

import com.cdcrane.stockflowbackend.products.categories.dto.CreateCategoryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CategoryUseCase {

    Page<Category> getAllCategories(Pageable pageable);

    Category getCategoryById(UUID categoryId);

    void createCategory(CreateCategoryDTO category);

    Category updateCategory(UUID categoryId, String categoryName);
}
