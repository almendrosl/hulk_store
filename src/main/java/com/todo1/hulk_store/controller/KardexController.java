package com.todo1.hulk_store.controller;

import com.todo1.hulk_store.model.Kardex;
import com.todo1.hulk_store.model.Transaction;
import com.todo1.hulk_store.service.KardexService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Api REST for Kardex Data with the weighted average path
 */
@RequestMapping("api/v1/kardex")
@RestController
public class KardexController {

    private final KardexService kardexService;

    public KardexController(KardexService kardexService) {
        this.kardexService = kardexService;
    }

    /**
     * Make a transaction for the product required
     *
     * @param transaction have to be an INPUT or OUTPUT
     * @return the new kardex Row created
     * @throws Exception RecordNotFoundException if the product id doesn't exist (Respond Status NOT FOUND)
     *                   NotValidStockException if the result stock is 0 or less throws this exception (Responds Status BAD REQUEST)
     */
    @PostMapping
    public Kardex addTransaction(@RequestBody Transaction transaction) throws Exception {
        return kardexService.makeTransaction(transaction);
    }

    /**
     * Returns the kardex card cplete
     *
     * @param productID Product id from the kardex
     * @return kardex card
     * @throws Exception RecordNotFoundException if the product id doesn't exist (Respond Status NOT FOUND)
     */
    @GetMapping(value = "/{productID}")
    @ResponseBody
    public List<Kardex> products(@PathVariable Integer productID) throws Exception {
        return kardexService.getKardex(productID);
    }
}
