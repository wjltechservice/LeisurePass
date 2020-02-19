package com.wjltechservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to be used when a requested customer object cannot be found
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No customer found for given ID")
public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long customerId) {
        super(String.format("No customer found for given id [%s]", customerId));
    }
}
