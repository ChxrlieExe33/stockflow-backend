package com.cdcrane.stockflowbackend.product_instances;

import com.cdcrane.stockflowbackend.product_instances.dto.ProductInstanceCountDTO;
import com.cdcrane.stockflowbackend.products.Product;
import com.cdcrane.stockflowbackend.products.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ProductInstanceRepositoryTest {

    @Autowired
    private ProductInstanceRepository underTest;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        underTest.deleteAll();
    }

    @Test
    void shouldCountByWidthLengthAndColour() {

        // Given
        Product root = Product.builder()
                .name("Test Product").build();

        productRepository.save(root);

        ProductInstance instance1 = ProductInstance.builder().product(root).width(10).length(10).colour("Red").build();
        ProductInstance instance2 = ProductInstance.builder().product(root).width(10).length(10).colour("Red").build();

        underTest.saveAll(List.of(instance1, instance2));

        // When
        List<ProductInstanceCountDTO> result = underTest.getCountByProductIdGroupByWidthLengthColour(root.getId());

        // Then
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.getFirst().count()).isEqualTo(2);

    }

    @Test
    void shouldCountByWidthLengthAndColourButReturnNone(){

        // Given
        Product root = Product.builder()
                .name("Test Product").build();

        productRepository.save(root);

        ProductInstance instance = ProductInstance.builder().product(root).width(10).length(10).colour("Red").build();

        underTest.save(instance);

        // When
        List<ProductInstanceCountDTO> result = underTest.getCountByProductIdGroupByWidthLengthColour(UUID.randomUUID());

        // Then
        assertThat(result.isEmpty()).isTrue();

    }

    @Test
    void shouldCountByWidthLength() {

        // Given
        Product root = Product.builder()
                .name("Test Product").build();

        productRepository.save(root);

        ProductInstance instance1 = ProductInstance.builder().product(root).width(10).length(10).build();
        ProductInstance instance2 = ProductInstance.builder().product(root).width(10).length(10).build();

        underTest.saveAll(List.of(instance1, instance2));

        // When
        List<ProductInstanceCountDTO> result = underTest.getCountByProductIdGroupByWidthLength(root.getId());

        // Then
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.getFirst().count()).isEqualTo(2);

    }

    @Test
    void shouldCountByWidthLengthButReturnNone(){

        // Given
        Product root = Product.builder()
                .name("Test Product").build();

        productRepository.save(root);

        ProductInstance instance = ProductInstance.builder().product(root).width(10).length(10).build();

        underTest.save(instance);

        // When
        List<ProductInstanceCountDTO> result = underTest.getCountByProductIdGroupByWidthLength(UUID.randomUUID());

        // Then
        assertThat(result.isEmpty()).isTrue();

    }

    @Test
    void shouldCountByColour() {

        // Given
        Product root = Product.builder()
                .name("Test Product").build();

        productRepository.save(root);

        ProductInstance instance1 = ProductInstance.builder().product(root).colour("Red").build();
        ProductInstance instance2 = ProductInstance.builder().product(root).colour("Red").build();

        underTest.saveAll(List.of(instance1, instance2));

        // When
        List<ProductInstanceCountDTO> result = underTest.getCountByProductIdGroupByColour(root.getId());

        // Then
        assertThat(result.isEmpty()).isFalse();
        assertThat(result.getFirst().count()).isEqualTo(2);

    }

    @Test
    void shouldCountByColourButReturnNone(){

        // Given
        Product root = Product.builder()
                .name("Test Product").build();

        productRepository.save(root);

        ProductInstance instance = ProductInstance.builder().product(root).colour("Red").build();

        underTest.save(instance);

        // When
        List<ProductInstanceCountDTO> result = underTest.getCountByProductIdGroupByColour(UUID.randomUUID());

        // Then
        assertThat(result.isEmpty()).isTrue();

    }

}