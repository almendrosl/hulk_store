package com.todo1.hulk_store.service;

import com.todo1.hulk_store.exeptions.RecordNotFoundException;
import com.todo1.hulk_store.model.Product;
import com.todo1.hulk_store.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void addProductTest() {
        String toTest = "TestProduct";
        Product product = new Product();
        product.setName(toTest);
        when(productRepository.save(any(Product.class))).then(returnsFirstArg());
        Product savedProduct = productService.addProduct(product);
        assertThat(savedProduct.getName()).isEqualTo(toTest);
    }

    @Test
    void getProductsTest() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productRepository.findAll()).thenReturn(products);
        List<Product> productsResponse = (List<Product>) productService.getProducts();
        assertThat(productsResponse).isNotEmpty();
        assertThat(productsResponse.size()).isEqualTo(2);
        assertThat(productsResponse).isEqualTo(products);
    }

    @Test
    void getProductTest() throws RecordNotFoundException {
        String toTest = "TestProduct";
        Product product = new Product();
        product.setName(toTest);
        when(productRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(product));
        Product savedProduct = productService.getProduct(1);
        assertThat(savedProduct.getName()).isEqualTo(toTest);
    }

    @Test
    void getProductExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> {
            productService.getProduct(1);
        });
    }

    @Test
    void deleteProductTest() throws RecordNotFoundException {
        String toTest = "TestProduct";
        Product product = new Product();
        product.setName(toTest);
        when(productRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(product));
        productService.deleteProduct(1);
        verify(productRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteProductExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> {
            productService.deleteProduct(2);
        });
    }

    @Test
    void updateProductTest() throws RecordNotFoundException {
        String toTest = "TestProduct";
        Product product = new Product();
        product.setName(toTest);
        product.setId(1);
        when(productRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(product));
        String newToTest = "NewTestProduct";
        Product newProduct = new Product();
        newProduct.setId(1);
        newProduct.setName(newToTest);
        when(productRepository.save(any(Product.class))).then(returnsFirstArg());
        Product updatedProduct = productService.updateProduct(newProduct);
        assertThat(updatedProduct.getName()).isEqualTo(newToTest);
    }

    @Test
    void updateProductExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> {
            productService.updateProduct(new Product());
        });
    }

}
