package com.todo1.hulk_store.repository;

import com.todo1.hulk_store.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("fakeDao")
public class FakeProductDataAccessService implements ProductDao {

    private static List<Product> DB = new ArrayList<>();

    @Override
    public int insertProduct(UUID id, Product product) {
        DB.add(new Product(id, product.getName()));
        return 1;
    }

    @Override
    public List<Product> selectAllProducts() {
        return DB;
    }
}
