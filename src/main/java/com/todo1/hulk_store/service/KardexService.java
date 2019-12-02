package com.todo1.hulk_store.service;

import com.todo1.hulk_store.exeptions.NotValidStockException;
import com.todo1.hulk_store.exeptions.RecordNotFoundException;
import com.todo1.hulk_store.model.Kardex;
import com.todo1.hulk_store.model.Transaction;
import com.todo1.hulk_store.model.TransactionType;
import com.todo1.hulk_store.repository.KardexRepository;
import com.todo1.hulk_store.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KardexService {

    final private static int MINIMUM_STOCK = 0;
    @Autowired
    private KardexRepository kardexRepository;
    @Autowired
    private ProductRepository productRepository;

    public Kardex makeTransaction(Transaction transaction) throws NotValidStockException, RecordNotFoundException {
        if (!productRepository.findById(transaction.getProductID()).isPresent()) {
            throw new RecordNotFoundException("No product record exist for given id");
        }
        switch (transaction.getType()) {
            case INPUT:
                return makeTransactionInput(transaction);
            case OUTPUT:
                return makeTransactionOutput(transaction);
        }

        return null;
    }

    public List<Kardex> getKardex(Integer productID) throws RecordNotFoundException {
        if (!productRepository.findById(productID).isPresent()) {
            throw new RecordNotFoundException("No product record exist for given id");
        }

        return kardexRepository.findByProduct_Id(productID);
    }

    private Kardex makeTransactionOutput(Transaction transaction) throws NotValidStockException {
        List<Kardex> kardex = kardexRepository.findByProduct_Id(transaction.getProductID());
        if (kardex.isEmpty()) {
            throw new NotValidStockException();
        }

        Kardex lastTransaction = kardex.get(kardex.size() - 1);

        Integer stockQuantity = lastTransaction.getStockQuantity() - transaction.getQuantity();

        if (stockQuantity <= MINIMUM_STOCK) {
            throw new NotValidStockException();
        }

        Double outputValue = lastTransaction.getUnitValue() * transaction.getQuantity();

        Double stockValue = lastTransaction.getUnitValue() * stockQuantity;

        return insertOutput(transaction, lastTransaction.getUnitValue(), outputValue, stockQuantity, stockValue);
    }

    private Kardex makeTransactionInput(Transaction transaction) {
        Double inputValue = transaction.getUnitPrice() * transaction.getQuantity();

        List<Kardex> kardex = kardexRepository.findByProduct_Id(transaction.getProductID());
        if (kardex.isEmpty()) {
            return insertInput(transaction, transaction.getUnitPrice(), inputValue, transaction.getQuantity(), inputValue);
        }
        Kardex lastTransaction = kardex.get(kardex.size() - 1);

        Integer stockQuantity = lastTransaction.getStockQuantity() + transaction.getQuantity();

        Double unitValue = (inputValue + lastTransaction.getStockValue()) / stockQuantity;

        Double stockValue = unitValue * stockQuantity;

        return insertInput(transaction, unitValue, inputValue, stockQuantity, stockValue);
    }

    private Kardex insertInput(Transaction transaction, Double unitValue, Double inputValue, Integer stockQuantity, Double stockValue) {
        Kardex kardex = new Kardex();
        kardex.setDate(transaction.getDate());
        kardex.setProduct(productRepository.findById(transaction.getProductID()).get());
        kardex.setDescription(transaction.getDescription());
        kardex.setUnitValue(unitValue);
        kardex.setInputQuantity(transaction.getQuantity());
        kardex.setInputValue(inputValue);
        kardex.setStockQuantity(stockQuantity);
        kardex.setStockValue(stockValue);
        kardex.setType(TransactionType.INPUT);
        return kardexRepository.save(kardex);
    }

    private Kardex insertOutput(Transaction transaction, Double unitValue, Double outputValue, Integer stockQuantity, Double stockValue) {
        Kardex kardex = new Kardex();
        kardex.setDate(transaction.getDate());
        kardex.setProduct(productRepository.findById(transaction.getProductID()).get());
        kardex.setDescription(transaction.getDescription());
        kardex.setUnitValue(unitValue);
        kardex.setOutputQuantity(transaction.getQuantity());
        kardex.setOutputValue(outputValue);
        kardex.setStockQuantity(stockQuantity);
        kardex.setStockValue(stockValue);
        kardex.setType(TransactionType.OUTPUT);
        return kardexRepository.save(kardex);
    }
}
