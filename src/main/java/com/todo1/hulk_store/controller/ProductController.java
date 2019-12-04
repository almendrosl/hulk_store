package com.todo1.hulk_store.controller;

import com.todo1.hulk_store.model.Product;
import com.todo1.hulk_store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest-Layer for Products Related Information
 */
@RequestMapping("api/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /**
     * Save a new Product
     *
     * @param product Product data that was saved
     * @return The new product saved
     */
    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    /**
     * Returns a product form its id
     *
     * @param id The product id required
     * @return The product from its id
     * @throws Exception RecordNotFoundException if the product id doesn't exist (Respond Status NOT FOUND)
     */
    @GetMapping(value = "/{id}")
    public Product getProduct(@PathVariable Integer id) throws Exception {
        return productService.getProduct(id);
    }

    /**
     * Call to all products
     *
     * @return all the products saved
     */
    @GetMapping
    @ResponseBody
    public List<Product> products() {
        return (List<Product>) productService.getProducts();
    }

    /**
     * Delete a product based in their id
     *
     * @param id Product id to eliminate
     * @throws Exception RecordNotFoundException if the product id doesn't exist (Respond Status NOT FOUND)
     */
    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable Integer id) throws Exception {
        productService.deleteProduct(id);
    }

    /**
     * Update the data form a product
     *
     * @param product new Product object to update (the id have to be the same form the product to update)
     * @return the product updated
     * @throws Exception RecordNotFoundException if the product id doesn't exist (Respond Status NOT FOUND)
     */
    @PutMapping
    @ResponseBody
    public Product updateProduct(@RequestBody Product product) throws Exception {
        return productService.updateProduct(product);
    }
}
