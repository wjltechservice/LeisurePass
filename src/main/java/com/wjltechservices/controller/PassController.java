package com.wjltechservices.controller;

import com.wjltechservices.database.model.Pass;
import com.wjltechservices.service.PassService;
import com.wjltechservices.service.dto.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for pass endpoints
 */
@RestController
@RequestMapping("pass")
public class PassController {

    private final PassService passService;

    @Autowired
    public PassController(PassService passService) {
        this.passService = passService;
    }

    /**
     * Add a new pass to the system
     *
     * @param vendorId     Vendor of the pass attraction
     * @param customerId   Customer purchasing the pass
     * @param passCity     City the attraction is in
     * @param validFrom    Midnight UTC of the day the pass is valid from
     * @param durationDays Number of days the pass is valid for
     * @return The details of the created pass
     */
    @PostMapping("/new")
    public Pass addPass(@RequestParam String vendorId,
                        @RequestParam Long customerId,
                        @RequestParam String passCity,
                        @RequestParam Long validFrom,
                        @RequestParam Integer durationDays) {
        return passService.addPass(vendorId, customerId, passCity, validFrom, durationDays);
    }

    /**
     * Renew an existing or expired pass
     * <p>
     * Will reset the validFrom time to be midnight today (UTC)
     *
     * @param passId     ID of the pass to renew
     * @param customerId Customer who is renewing their pass
     * @return The details of the renewed pass
     */
    @PatchMapping("/{passId}/{customerId}/renew")
    public Pass renewPass(@PathVariable String passId, @PathVariable Long customerId) {
        return passService.renewPass(passId, customerId);
    }

    /**
     * Validate whether a pass is valid
     * <p>
     * A pass is valid so long as the time the pass is valid from + its duration in days has not yet been reached
     *
     * @param passId   Id of the pass to validate
     * @param vendorId Vendor who supplied the pass
     * @return Validation object saying if the pass is valid or not, along with the pass and vendor IDs
     */
    @GetMapping("/{passId}/{vendorId}/validate")
    public Validation validatePass(@PathVariable String passId, @PathVariable String vendorId) {
        return passService.validatePass(passId, vendorId);
    }

    /**
     * Cancel a customer's pass
     * <p>
     * If a pass exists in the system, valid or expired, it will be removed
     *
     * @param passId     ID of the pass to be cancelled
     * @param customerId Customer who is cancelling their pass
     */
    @DeleteMapping("/{passId}/{customerId}/cancel")
    public void cancelPass(@PathVariable String passId, @PathVariable Long customerId) {
        passService.cancelPass(passId, customerId);
    }

}
