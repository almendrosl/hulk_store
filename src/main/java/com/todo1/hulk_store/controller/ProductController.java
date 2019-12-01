package com.todo1.hulk_store.controller;

import com.todo1.hulk_store.model.Product;
import com.todo1.hulk_store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1/product")
@RestController
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

//    @PostMapping
//    public void addProduct(@RequestBody Product product) {
//        productService.addProduct(product);
//    }

    @GetMapping
    @ResponseBody
    public String products() {
        Iterable<Product> all = productService.getProducts();

        StringBuilder sb = new StringBuilder();

        all.forEach(p -> sb.append(p.getName() + "<br>"));

        return sb.toString();
    }
}
