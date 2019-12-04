package com.todo1.hulk_store.repository;

import com.todo1.hulk_store.model.Product;
import org.springframework.data.repository.CrudRepository;

/**
 * Interface to interact whit Product saved or to save in the DB
 */
public interface ProductRepository extends CrudRepository<Product, Integer> {
}
