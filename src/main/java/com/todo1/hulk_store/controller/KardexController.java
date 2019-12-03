package com.todo1.hulk_store.controller;

import com.todo1.hulk_store.model.Kardex;
import com.todo1.hulk_store.model.Transaction;
import com.todo1.hulk_store.service.KardexService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("api/v1/kardex")
@RestController
public class KardexController {

    private final KardexService kardexService;

    public KardexController(KardexService kardexService) {
        this.kardexService = kardexService;
    }

    @PostMapping
    public Kardex addTransaction(@RequestBody Transaction transaction) throws Exception {
        return kardexService.makeTransaction(transaction);
    }

    @GetMapping(value = "/{productID}")
    @ResponseBody
    public List<Kardex> products(@PathVariable Integer productID) throws Exception {
        return kardexService.getKardex(productID);
    }
}
