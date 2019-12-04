package com.todo1.hulk_store.service;

import com.todo1.hulk_store.exeptions.RecordNotFoundException;
import com.todo1.hulk_store.model.Product;
import com.todo1.hulk_store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Logic for the product resource
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Add a new product
     *
     * @param product product object to save
     * @return the new Product saved
     */
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    /**
     * Obtain a specific product from its id
     *
     * @param productID the product id for the product required
     * @return the product required
     * @throws RecordNotFoundException if the product id doesn't exist
     */
    public Product getProduct(Integer productID) throws RecordNotFoundException {
        Optional<Product> product = productRepository.findById(productID);

        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RecordNotFoundException("No product record exist for given id");
        }
    }

    /**
     * Call to all products
     *
     * @return All the products in the DB
     */
    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    /**
     * delete a specific product
     *
     * @param productID the product id to delete
     * @throws RecordNotFoundException if the product id doesn't exist
     */
    public void deleteProduct(Integer productID) throws RecordNotFoundException {
        Optional<Product> product = productRepository.findById(productID);

        if (product.isPresent()) {
            productRepository.deleteById(productID);
        } else {
            throw new RecordNotFoundException("No product record exist for given id");
        }
    }

    /**
     * Update the data form a product
     *
     * @param product new Product object to update (the id have to be the same form the product to update)
     * @return the product updated
     * @throws RecordNotFoundException if the product id doesn't exist
     */
    public Product updateProduct(Product product) throws RecordNotFoundException {
        Optional<Product> productToUpdate = productRepository.findById(product.getId());
        if (productToUpdate.isPresent()) {
            Product newProduct = productToUpdate.get();
            newProduct.setName(product.getName());
            return productRepository.save(newProduct);
        } else {
            throw new RecordNotFoundException("No product record exist for given id");
        }
    }
}
