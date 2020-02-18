package com.wjltechservices.service;

import com.wjltechservices.database.model.Vendor;
import com.wjltechservices.database.repository.VendorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VendorService {

    private final VendorRepository vendorRepository;

    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    public Vendor addVendor(String vendorName) {
        if (vendorName == null || vendorName.isEmpty()) {
            throw new IllegalArgumentException("Vendor name cannot be null or empty");
        }

        String vendorId = vendorName.toLowerCase().replaceAll("\\s", "");

        Vendor vendor = new Vendor();
        vendor.setVendorName(vendorName);
        vendor.setVendorId(vendorId);

        vendorRepository.save(vendor);

        return vendor;
    }

    public Optional<Vendor> getVendor(String vendorId) {
        return vendorRepository.findById(vendorId);
    }
}
