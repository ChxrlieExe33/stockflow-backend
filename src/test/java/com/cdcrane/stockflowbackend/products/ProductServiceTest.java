package com.cdcrane.stockflowbackend.products;

import com.cdcrane.stockflowbackend.authentication.SecurityUtils;
import com.cdcrane.stockflowbackend.config.exceptions.ResourceNotFoundException;
import com.cdcrane.stockflowbackend.products.categories.Category;
import com.cdcrane.stockflowbackend.products.dto.CreateProductDTO;
import com.cdcrane.stockflowbackend.products.dto.ProductDTO;
import com.cdcrane.stockflowbackend.users.ApplicationUser;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    private ProductService underTest;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private SecurityUtils securityUtils;

    @Mock
    private EntityManager em;

    @BeforeEach
    void setUp() {
        underTest = new ProductService(productRepository, securityUtils, em);
    }

    @Test
    void shouldGetProductById() {

        // Given
        ApplicationUser currentUser = ApplicationUser.builder().username("test").build();

        Product product = Product.builder()
                .id(java.util.UUID.randomUUID())
                .name("Test Product")
                .factoryName("Test Factory")
                .createdBy(currentUser)
                .build();

        given(productRepository.findById(any())).willReturn(Optional.of(product));

        // When
        ProductDTO result = underTest.getProductById(product.getId());

        // Then
        assertThat(result).isNotNull();

        assertThat(result.name()).isEqualTo(product.getName());

    }

    @Test
    void shouldTryGetProductByIdAndThrowExceptionWhenProductNotFound() {

        // Given
        given(productRepository.findById(any())).willReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> underTest.getProductById(java.util.UUID.randomUUID()))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void shouldGetProductsByCategoryId() {

        // Given
        ApplicationUser creator = ApplicationUser.builder().username("test").build();

        Category category = Category.builder().id(UUID.randomUUID()).name("Random Category").build();

        Product p1 = Product.builder().id(UUID.randomUUID()).name("Product 1").category(category).createdBy(creator).build();
        Product p2 = Product.builder().id(UUID.randomUUID()).name("Product 2").category(category).createdBy(creator).build();

        Page<Product> products = new PageImpl<>(List.of(p1, p2));

        given(productRepository.findByCategoryId(category.getId(), Pageable.unpaged())).willReturn(products);

        // When
        Page<ProductDTO> result = underTest.getByCategoryId(category.getId(), Pageable.unpaged());

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getTotalElements()).isEqualTo(2);

    }

    @Test
    void shouldTryGetProductsByCategoryIdAndThrowExceptionWhenNoneFound() {

        // Given
        given(productRepository.findByCategoryId(any(), any())).willReturn(Page.empty());

        // Then
        assertThatThrownBy(() -> underTest.getByCategoryId(UUID.randomUUID(), Pageable.unpaged()))
                .isInstanceOf(ResourceNotFoundException.class);

    }

    @Test
    void shouldCreateProduct() {

        // Given
        CreateProductDTO input = new CreateProductDTO("Product name", "Factory name", true, true, false, false, UUID.randomUUID());

        ApplicationUser creator = ApplicationUser.builder().username("test").build();
        Category category = Category.builder().id(UUID.randomUUID()).name("Random Category").build();

        given(em.getReference(any(), any())).willReturn(category);
        given(securityUtils.getCurrentAuth()).willReturn(creator);


        // When
        underTest.createProduct(input);

        // Then
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);

        verify(productRepository).save(productCaptor.capture());

        Product product = productCaptor.getValue();
        assertThat(product.getName()).isEqualTo(input.name());
        assertThat(product.getCreatedBy().getUsername()).isEqualTo(creator.getUsername());
        assertThat(product.getCategory().getName()).isEqualTo(category.getName());


    }

}