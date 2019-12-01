package com.todo1.hulk_store.controller;

import com.todo1.hulk_store.model.Product;
import com.todo1.hulk_store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @GetMapping(value = "/{id}")
    public Product getProduct(@PathVariable Integer id) throws Exception {
        return productService.getProduct(id);
    }

    @GetMapping
    @ResponseBody
    public List<Product> products() {
        return (List<Product>) productService.getProducts();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteProduct(@PathVariable Integer id) throws Exception {
        productService.deleteProduct(id);
    }

    @PutMapping
    @ResponseBody
    public Product updateProduct(@RequestBody Product product) throws Exception {
        return productService.updateProduct(product);
    }
}
