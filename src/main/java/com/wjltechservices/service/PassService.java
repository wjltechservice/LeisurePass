package com.wjltechservices.service;

import com.wjltechservices.database.model.Customer;
import com.wjltechservices.database.model.Pass;
import com.wjltechservices.database.model.Vendor;
import com.wjltechservices.database.repository.PassRepository;
import com.wjltechservices.exception.CustomerNotFoundException;
import com.wjltechservices.exception.PassNotFoundException;
import com.wjltechservices.exception.VendorNotFoundException;
import com.wjltechservices.service.dto.Validation;
import com.wjltechservices.utilities.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service methods for pass functionality
 */
@Service
public class PassService {

    public static final int ONE_DAY = 86400;
    private final PassRepository passRepository;
    private final VendorService vendorService;
    private final CustomerService customerService;
    private final PassIdGenerator passIdGenerator;

    @Autowired
    public PassService(PassRepository passRepository, VendorService vendorService, CustomerService customerService, PassIdGenerator passIdGenerator) {
        this.passRepository = passRepository;
        this.vendorService = vendorService;
        this.customerService = customerService;
        this.passIdGenerator = passIdGenerator;
    }

    /**
     * Add a new pass to the system
     *
     * @param vendorId     ID of the vendor of the attraction
     * @param customerId   ID of the customer purchasing the pass
     * @param passCity     Location of the attraction
     * @param validFrom    Midnight (UTC) of the day the pass is valid from
     * @param durationDays Duration in days of the pass
     * @return The details of the created pass
     */
    public Pass addPass(String vendorId, Long customerId, String passCity, Long validFrom, Integer durationDays) {
        validateParameters(vendorId, customerId, validFrom, durationDays);

        // Get the details of the vendor and customer we are linking
        Vendor vendor = vendorService.getVendor(vendorId).orElseThrow(() -> new VendorNotFoundException(vendorId));
        Customer customer = customerService.getCustomer(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));

        // Generate a complex PassId so as to keep it unique
        String passId = passIdGenerator.generateId(vendorId, customerId);

        Pass pass = Pass.PassBuilder.aPass()
                .withPassId(passId)
                .withVendor(vendor)
                .withCustomer(customer)
                .withPassCity(passCity)
                .withValidFrom(validFrom)
                .withDurationDays(durationDays)
                .build();

        return passRepository.save(pass);
    }

    /**
     * Renew a valid or expired pass that is present in the system already
     *
     * @param passId     ID of the pass to be renewed
     * @param customerId ID of the customer who is renewing their pass
     * @return The details of the renewed pass, or PassNotFoundException if no pass was found with the given details
     */
    public Pass renewPass(String passId, Long customerId) {
        Pass pass = passRepository.findPassByPassIdAndCustomerId(passId, customerId).orElseThrow(() -> PassNotFoundException.notFoundForCustomer(passId, customerId));

        pass.setValidFrom(TimeUtils.todayAtMidnightEpoch());

        return passRepository.save(pass);
    }

    /**
     * Validate whether a pass is within its expiry
     *
     * @param passId   Id of the Pass to validate
     * @param vendorId Vendor who is providing the attraction
     * @return Validation object spcifying if the pass is valid, the vendorId and passId, or PassNotFoundException
     * if no pass is found for the provided details
     */
    public Validation validatePass(String passId, String vendorId) {
        Pass pass = passRepository.findPassByPassIdAndVendorId(passId, vendorId).orElseThrow(() -> PassNotFoundException.notFoundForVendor(passId, vendorId));

        // Get the details of when the pass started, and how long it lasts
        Long validFrom = pass.getValidFrom();
        Integer durationDays = pass.getDurationDays();

        Long todayAtMidnight = TimeUtils.todayAtMidnightEpoch();
        boolean isValid = false;
        // The pass is valid if the epoch for today at midnight is smaller than the starting epoch + duration of the pass
        if (validFrom + (durationDays * ONE_DAY) > todayAtMidnight) {
            isValid = true;
        }
        return new Validation(passId, vendorId, isValid);
    }

    /**
     * Cancel a pass
     * <p>
     * Will remove any valid or expired pass found matching the given details or throws a PassNotFoundException
     * if no pass is found matching the given details
     *
     * @param passId     ID of the pass to cancel
     * @param customerId ID of the customer who is cancelling
     */
    public void cancelPass(String passId, Long customerId) {
        Pass pass = passRepository.findPassByPassIdAndCustomerId(passId, customerId).orElseThrow(() -> PassNotFoundException.notFoundForCustomer(passId, customerId));

        passRepository.delete(pass);
    }

    /**
     * Validate the paramters for addPass such that no parameter is null, empty or negative
     *
     * @param vendorId   Id of the vendor
     * @param customerId Id of the customer
     * @param validFrom  Midnight (UTC) of the day the pass is valid from
     * @param durationDays Duration in days for the pass
     */
    private void validateParameters(String vendorId, Long customerId, Long validFrom, Integer durationDays) {
        if (vendorId == null || vendorId.isEmpty()) {
            throw new IllegalArgumentException("Vendor ID cannot be null or empty");
        }

        if (customerId == null || customerId < 1 || validFrom == null || validFrom < 1 || durationDays == null || durationDays < 1) {
            throw new IllegalArgumentException("customerId, validFrom and durationDays must all be positive, non-null values");
        }
    }
}
