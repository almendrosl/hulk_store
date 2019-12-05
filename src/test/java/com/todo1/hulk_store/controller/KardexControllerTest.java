package com.todo1.hulk_store.controller;

import com.todo1.hulk_store.exeptions.NotValidStockException;
import com.todo1.hulk_store.exeptions.RecordNotFoundException;
import com.todo1.hulk_store.model.Kardex;
import com.todo1.hulk_store.model.Product;
import com.todo1.hulk_store.model.Transaction;
import com.todo1.hulk_store.model.TransactionType;
import com.todo1.hulk_store.service.KardexService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(KardexController.class)
public class KardexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private KardexService kardexService;

    @Test
    public void performATransactionShouldReturnAKardexRow() throws Exception {
        Kardex k = new Kardex();
        k.setType(TransactionType.INPUT);
        k.setInputQuantity(43);
        k.setInputValue(56.0);
        k.setStockQuantity(1000);
        k.setStockValue(400.0);
        k.setUnitValue(35.0);
        k.setDescription("Algo");
        k.setProduct(new Product());
        when(kardexService.makeTransaction(any(Transaction.class))).thenReturn(k);
        this.mockMvc.perform(post("/api/v1/kardex/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"type\": \"INPUT\",\n" +
                        "    \"date\": \"2020-12-12\",\n" +
                        "    \"description\": \"Algo\",\n" +
                        "    \"quantity\": 43,\n" +
                        "    \"unitPrice\": 56,\n" +
                        "    \"productID\": 1\n" +
                        "}"))
                .andExpect(status().isOk());

    }

    @Test
    public void performATransactionShouldARecordNotFoundException() throws Exception {
        when(kardexService.makeTransaction(any(Transaction.class))).thenThrow(new RecordNotFoundException("message"));
        this.mockMvc.perform(post("/api/v1/kardex/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"type\": \"INPUT\",\n" +
                        "    \"date\": \"2020-12-12\",\n" +
                        "    \"description\": \"Algo\",\n" +
                        "    \"quantity\": 43,\n" +
                        "    \"unitPrice\": 56,\n" +
                        "    \"productID\": 1\n" +
                        "}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void performATransactionShouldANotValidStockException() throws Exception {
        when(kardexService.makeTransaction(any(Transaction.class))).thenThrow(new NotValidStockException());
        this.mockMvc.perform(post("/api/v1/kardex/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"type\": \"INPUT\",\n" +
                        "    \"date\": \"2020-12-12\",\n" +
                        "    \"description\": \"Algo\",\n" +
                        "    \"quantity\": 43,\n" +
                        "    \"unitPrice\": 56,\n" +
                        "    \"productID\": 1\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getKardexCard() throws Exception {
        List<Kardex> kardex = new ArrayList<>();
        when(kardexService.getKardex(anyInt())).thenReturn(kardex);
        this.mockMvc.perform(get("/api/v1/kardex/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void getKardexCardThenThrowARecordNotFoundException() throws Exception {
        when(kardexService.getKardex(anyInt())).thenThrow(new RecordNotFoundException("message"));
        this.mockMvc.perform(get("/api/v1/kardex/1"))
                .andExpect(status().isNotFound());
    }
}
