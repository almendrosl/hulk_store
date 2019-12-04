package com.todo1.hulk_store.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception that represent the invalid transaction stock calculated
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotValidStockException extends Exception {

    public NotValidStockException() {
        super("Invalid Stock transaction");
    }
}
