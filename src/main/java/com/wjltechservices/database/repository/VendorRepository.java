package com.wjltechservices.database.repository;

import com.wjltechservices.database.model.Vendor;
import org.springframework.data.repository.CrudRepository;

public interface VendorRepository extends CrudRepository<Vendor, String> {
}
