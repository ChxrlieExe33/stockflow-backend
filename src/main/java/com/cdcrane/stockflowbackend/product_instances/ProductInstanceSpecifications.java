package com.cdcrane.stockflowbackend.product_instances;

import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

/**
 * Used for any queries that have optional query parameters.
 */
public class ProductInstanceSpecifications {

    public static Specification<ProductInstance> byRootProductId(UUID productId) {
        return (root, query, cb) ->
                productId == null ? null : cb.equal(root.get("product").get("id"), productId);
    }

    public static Specification<ProductInstance> byReserved(boolean reserved) {
        return (root, query, cb) ->
                reserved ? cb.isTrue(root.get("reserved")) : cb.isFalse(root.get("reserved"));
    }

    public static Specification<ProductInstance> hasWidth(Integer width) {
        return (root, query, cb) ->
                width == null ? null : cb.equal(root.get("width"), width);
    }

    public static Specification<ProductInstance> hasLength(Integer length) {
        return (root, query, cb) ->
                length == null ? null : cb.equal(root.get("length"), length);
    }

    public static Specification<ProductInstance> hasHeight(Integer height) {
        return (root, query, cb) ->
                height == null ? null : cb.equal(root.get("height"), height);
    }

    public static Specification<ProductInstance> hasColour(String colour) {
        return (root, query, cb) ->
                colour == null ? null : cb.equal(root.get("colour"), colour);
    }
}
