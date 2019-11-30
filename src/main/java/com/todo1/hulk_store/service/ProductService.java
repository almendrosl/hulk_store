package com.todo1.hulk_store.service;

import com.todo1.hulk_store.model.Product;
import com.todo1.hulk_store.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    @Autowired
    public ProductService(@Qualifier("fakeDao") ProductDao productDao) {
        this.productDao = productDao;
    }

    public int addProduct(Product product) {
        return productDao.insertProduct(product);
    }

    public List<Product> getProducts() {
        return productDao.selectAllProducts();
    }
}
