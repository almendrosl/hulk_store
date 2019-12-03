package com.todo1.hulk_store.service;

import com.todo1.hulk_store.exeptions.RecordNotFoundException;
import com.todo1.hulk_store.model.Product;
import com.todo1.hulk_store.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProduct(Integer productID) throws RecordNotFoundException {
        Optional<Product> product = productRepository.findById(productID);

        if (product.isPresent()) {
            return product.get();
        } else {
            throw new RecordNotFoundException("No product record exist for given id");
        }
    }

    public Iterable<Product> getProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(Integer productID) throws RecordNotFoundException {
        Optional<Product> product = productRepository.findById(productID);

        if (product.isPresent()) {
            productRepository.deleteById(productID);
        } else {
            throw new RecordNotFoundException("No product record exist for given id");
        }
    }

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
