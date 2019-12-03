package com.todo1.hulk_store.service;

import com.todo1.hulk_store.exeptions.NotValidStockException;
import com.todo1.hulk_store.exeptions.RecordNotFoundException;
import com.todo1.hulk_store.model.Kardex;
import com.todo1.hulk_store.model.Product;
import com.todo1.hulk_store.model.Transaction;
import com.todo1.hulk_store.model.TransactionType;
import com.todo1.hulk_store.repository.KardexRepository;
import com.todo1.hulk_store.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KardexServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private KardexRepository kardexRepository;

    @InjectMocks
    private KardexService kardexService;

    @Test
    void getKardexTest() throws RecordNotFoundException {
        List<Kardex> kardexList = new ArrayList<>();
        kardexList.add(new Kardex());
        kardexList.add(new Kardex());
        when(productRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(new Product()));
        when(kardexRepository.findByProduct_Id(any(Integer.class))).thenReturn(kardexList);
        List<Kardex> kardexResponse = kardexService.getKardex(1);
        assertThat(kardexResponse).isNotEmpty();
        assertThat(kardexResponse.size()).isEqualTo(2);
        assertThat(kardexResponse).isEqualTo(kardexList);
    }

    @Test
    void getKardexExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> {
            kardexService.getKardex(1);
        });
    }

    @Test
    void makeTransactionRecordNotFoundExceptionTest() {
        assertThrows(RecordNotFoundException.class, () -> {
            kardexService.makeTransaction(new Transaction());
        });
    }

    @Test
    void makeTransactionInputTest() throws NotValidStockException, RecordNotFoundException {
        Transaction transaction = newTransactionInputObject();
        Kardex lastTransaction = newKardexLastTransaction();
        List<Kardex> kardexList = new ArrayList<>();
        kardexList.add(lastTransaction);
        when(productRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(new Product()));
        when(kardexRepository.findByProduct_Id(anyInt())).thenReturn(kardexList);
        when(kardexRepository.save(any(Kardex.class))).then(returnsFirstArg());
        Kardex newKardex = kardexService.makeTransaction(transaction);
        assertThat(newKardex).isNotNull();
        assertThat(newKardex.getDate()).isEqualTo(transaction.getDate());
        assertThat(newKardex.getDescription()).isEqualTo(transaction.getDescription());
        assertThat(newKardex.getUnitValue()).isEqualTo(9.538461538461538);
        assertThat(newKardex.getInputQuantity()).isEqualTo(transaction.getQuantity());
        assertThat(newKardex.getInputValue()).isEqualTo(12000.0);
        assertThat(newKardex.getStockQuantity()).isEqualTo(1300);
        assertThat(newKardex.getStockValue()).isEqualTo(12400.0);
        assertThat(newKardex.getType()).isEqualTo(TransactionType.INPUT);
    }

    @Test
    void makeTransactionOutputTest() throws NotValidStockException, RecordNotFoundException {
        Transaction transaction = newTransactionOutputObject();
        Kardex lastTransaction = newKardexLastTransaction();
        List<Kardex> kardexList = new ArrayList<>();
        kardexList.add(lastTransaction);
        when(productRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(new Product()));
        when(kardexRepository.findByProduct_Id(anyInt())).thenReturn(kardexList);
        when(kardexRepository.save(any(Kardex.class))).then(returnsFirstArg());

        Kardex newKardex = kardexService.makeTransaction(transaction);

        assertThat(newKardex).isNotNull();
        assertThat(newKardex.getDate()).isEqualTo(transaction.getDate());
        assertThat(newKardex.getDescription()).isEqualTo(transaction.getDescription());
        assertThat(newKardex.getUnitValue()).isEqualTo(35.0);
        assertThat(newKardex.getOutputQuantity()).isEqualTo(transaction.getQuantity());
        assertThat(newKardex.getOutputValue()).isEqualTo(1050.0);
        assertThat(newKardex.getStockQuantity()).isEqualTo(970);
        assertThat(newKardex.getStockValue()).isEqualTo(33950.0);
        assertThat(newKardex.getType()).isEqualTo(TransactionType.OUTPUT);
    }

    @Test
    void makeTransactionOutputTestNotValidStockException() {
        Transaction transaction = newTransactionOutputObject();
        transaction.setQuantity(100000);
        Kardex lastTransaction = newKardexLastTransaction();
        List<Kardex> kardexList = new ArrayList<>();
        kardexList.add(lastTransaction);
        when(productRepository.findById(any(Integer.class))).thenReturn(java.util.Optional.of(new Product()));
        when(kardexRepository.findByProduct_Id(anyInt())).thenReturn(kardexList);

        assertThrows(NotValidStockException.class, () -> {
            kardexService.makeTransaction(transaction);
        });
    }

    private Transaction newTransactionInputObject() {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.INPUT);
        transaction.setDate(new Date());
        transaction.setDescription("New Transaction");
        transaction.setProductID(1);
        transaction.setQuantity(300);
        transaction.setUnitPrice(40.0);
        return transaction;
    }

    private Transaction newTransactionOutputObject() {
        Transaction transaction = new Transaction();
        transaction.setType(TransactionType.OUTPUT);
        transaction.setDate(new Date());
        transaction.setDescription("New OUTPUT Transaction");
        transaction.setProductID(1);
        transaction.setQuantity(30);
        transaction.setUnitPrice(40.0);
        return transaction;
    }

    private Kardex newKardexLastTransaction() {
        Kardex k = new Kardex();
        k.setStockQuantity(1000);
        k.setStockValue(400.0);
        k.setUnitValue(35.0);
        return k;
    }

}
