package com.cdcrane.stockflowbackend.products.categories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryUseCase {

    Page<Category> getAllCategories(Pageable pageable);
}
