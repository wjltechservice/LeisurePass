package com.wjltechservices.service;

import com.wjltechservices.database.model.Vendor;
import com.wjltechservices.database.model.Vendor.VendorBuilder;
import com.wjltechservices.database.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service for vendor operations
 */
@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    /**
     * Add a new vendor to the system
     *
     * @param vendorName Name of the vendor to add
     * @return Details of the created vendor
     */
    public Vendor addVendor(String vendorName) {
        if (vendorName == null || vendorName.isEmpty()) {
            throw new IllegalArgumentException("Vendor name cannot be null or empty");
        }

        String vendorId = vendorName.toLowerCase().replaceAll("\\s", "");

        Vendor vendor = VendorBuilder.aVendor()
                .withVendorId(vendorId)
                .withVendorName(vendorName)
                .build();

        return vendorRepository.save(vendor);
    }

    /**
     * Find a vendor in the system
     *
     * @param vendorId ID of the vendor to find
     * @return Optional of the any vendor details found
     */
    public Optional<Vendor> getVendor(String vendorId) {
        return vendorRepository.findById(vendorId);
    }
}
