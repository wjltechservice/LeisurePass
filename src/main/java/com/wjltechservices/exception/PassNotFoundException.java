package com.wjltechservices.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception to be used when a requested pass object cannot be found
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Pass could not be found for provided IDs")
public class PassNotFoundException extends RuntimeException {

    /**
     * Create a PassNotFoundException with passId and vendorId
     *
     * @param passId   Pass ID used for the lookup
     * @param vendorId VendorID used for the lookup
     * @return A PassNotFoundException with the relevant info
     */
    public static PassNotFoundException notFoundForVendor(String passId, String vendorId) {
        return new PassNotFoundException(String.format("No pass found for given passId [%s] and vendorId [%s]", passId, vendorId));
    }
    /**
     * Create a PassNotFoundException with passId and customerId
     *
     * @param passId   Pass ID used for the lookup
     * @param customerId Customer ID used for the lookup
     * @return A PassNotFoundException with the relevant info
     */
    public static PassNotFoundException notFoundForCustomer(String passId, Long customerId) {
        return new PassNotFoundException(String.format("No pass found for given passId [%s] and customerId [%s]", passId, customerId));
    }

    private PassNotFoundException(String message) {
        super(message);
    }
}