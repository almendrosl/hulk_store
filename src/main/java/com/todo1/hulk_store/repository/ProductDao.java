package com.todo1.hulk_store.repository;

import com.todo1.hulk_store.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductDao {

    int insertProduct(UUID id, Product product);

    default int insertProduct(Product product) {
        UUID id = UUID.randomUUID();
        return insertProduct(id, product);
    }

    List<Product> selectAllProducts();
}
