package com.wjltechservices.database.repository;

import com.wjltechservices.database.model.Customer;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository for customer objects
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
