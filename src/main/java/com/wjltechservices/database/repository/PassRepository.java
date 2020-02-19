package com.wjltechservices.database.repository;

import com.wjltechservices.database.model.Pass;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Repository for pass objects
 */
public interface PassRepository extends CrudRepository<Pass, String> {

    /**
     * Return a pass which matches the passId and customerId
     *
     * @param passId     Pass ID for query for
     * @param customerId Customer ID to query for
     * @return Optional of any pass found
     */
    @Query("SELECT p from Pass p WHERE p.passId = ?1 and p.customer.customerId = ?2")
    Optional<Pass> findPassByPassIdAndCustomerId(String passId, Long customerId);

    /**
     * Return a pass which matches the passId and vendorId
     *
     * @param passId   Pass ID for query for
     * @param vendorId Vendor ID to query for
     * @return Optional of any pass found
     */
    @Query("SELECT p from Pass p WHERE p.passId = ?1 and p.vendor.vendorId = ?2")
    Optional<Pass> findPassByPassIdAndVendorId(String passId, String vendorId);

}
