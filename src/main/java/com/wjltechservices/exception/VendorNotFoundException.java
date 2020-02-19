package com.wjltechservices.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to be used when a requested vendor object cannot be found
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "No vendor found for given ID")
public class VendorNotFoundException extends RuntimeException {

    public VendorNotFoundException(String vendorId) {
        super(String.format("No vendor found for given id [%s]", vendorId));
    }
}
