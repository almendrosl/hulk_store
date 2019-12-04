package com.todo1.hulk_store.controller;

import com.todo1.hulk_store.exeptions.RecordNotFoundException;
import com.todo1.hulk_store.model.Product;
import com.todo1.hulk_store.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void productGetIDShouldReturnAProduct() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("cosa");
        when(productService.getProduct(any(Integer.class))).thenReturn(product);
        this.mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("cosa")));
    }

    @Test
    public void productGetIDShouldReturnANotFoundStatus() throws Exception {
        when(productService.getProduct(any(Integer.class))).thenThrow(new RecordNotFoundException("message"));
        this.mockMvc.perform(get("/api/v1/product/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void productPostShouldReturnAProduct() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("cosa");
        when(productService.addProduct(any(Product.class))).thenReturn(product);
        this.mockMvc.perform(post("/api/v1/product/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"1\", \"name\": \"cosa\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("cosa")));
    }

    @Test
    public void productCallAllProducts() throws Exception {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        when(productService.getProducts()).thenReturn(products);
        this.mockMvc.perform(get("/api/v1/product"))
                .andExpect(status().isOk());
    }

    @Test
    public void productDeleteAProduct() throws Exception {
        doNothing().when(productService).deleteProduct(any(Integer.class));
        this.mockMvc.perform(delete("/api/v1/product/1")).andExpect(status().isOk());
    }

    @Test
    public void productDeleteAProductShouldReturnANotFoundStatus() throws Exception {
        doThrow(new RecordNotFoundException("message")).when(productService).deleteProduct(any(Integer.class));
        this.mockMvc.perform(delete("/api/v1/product/1")).andExpect(status().isNotFound());
    }

    @Test
    public void productUpdateProduct() throws Exception {
        Product product = new Product();
        product.setId(1);
        product.setName("cosa");
        when(productService.updateProduct(any(Product.class))).thenReturn(product);
        this.mockMvc.perform(put("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"1\", \"name\": \"cosa\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("cosa")));
    }

    @Test
    public void productUpdateAProductShouldReturnANotFoundStatus() throws Exception {
        doThrow(new RecordNotFoundException("message")).when(productService).updateProduct(any(Product.class));
        this.mockMvc.perform(put("/api/v1/product")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\": \"1\", \"name\": \"cosa\"}"))
                .andExpect(status().isNotFound());
    }
}
