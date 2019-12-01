package com.todo1.hulk_store.service;

import com.todo1.hulk_store.model.Product;
import com.todo1.hulk_store.repository.KardexRepository;
import com.todo1.hulk_store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private KardexRepository kardexRepository;

//    private final ProductDao productDao;
//
//    @Autowired
//    public ProductService(@Qualifier("fakeDao") ProductDao productDao) {
//        this.productDao = productDao;
//    }
//
//    public int addProduct(Product product) {
//        return productDao.insertProduct(product);
//    }

    public Iterable<Product> getProducts() {
        return repository.findAll();
    }
}
