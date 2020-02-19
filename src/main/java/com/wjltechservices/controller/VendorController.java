package com.wjltechservices.controller;

import com.wjltechservices.database.model.Vendor;
import com.wjltechservices.exception.VendorNotFoundException;
import com.wjltechservices.service.VendorService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for vendor endpoints
 */
@RestController
@RequestMapping("vendor")
public class VendorController {

    private final VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    /**
     * Add a new vendor to the system. A vendorId will be generated and returned
     * <p>
     * Subsequent calls to create existing vendors will be idempotent
     *
     * @param vendorName Name of the vendor to add
     * @return The vendorId generated for that vendor
     */
    @PostMapping("/new")
    public Vendor addVendor(@RequestParam String vendorName) {
        return vendorService.addVendor(vendorName);
    }

    /**
     * Get an existing vendor
     * <p>
     * Will return a 404 if no vendor found
     *
     * @param vendorId Vendor to lookup
     * @return The vendor details, or 404 error if not found
     */
    @GetMapping("/{vendorId}")
    public Vendor getVendor(@PathVariable String vendorId) {
        return vendorService.getVendor(vendorId).orElseThrow(() -> new VendorNotFoundException(vendorId));
    }
}
