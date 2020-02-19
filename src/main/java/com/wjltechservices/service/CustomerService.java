package com.wjltechservices.service;

import com.wjltechservices.database.model.Customer;
import com.wjltechservices.database.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service methods for Customer operations
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    /**
     * Add a new customer to the system
     *
     * @param customerName Name of the customer
     * @param homeCity     Home city of the customer
     * @return the details of the created customer
     */
    public Customer addCustomer(String customerName, String homeCity) {
        if (customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Customer name cannot be null or empty");
        }
        if (homeCity == null || homeCity.isEmpty()) {
            throw new IllegalArgumentException("Home city cannot by null or empty");
        }

        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setHomeCity(homeCity);

        return customerRepository.save(customer);
    }

    /**
     * Get a customer from the repository
     *
     * @param customerId Id of the customer to look up
     * @return Optional of any customer found
     */
    public Optional<Customer> getCustomer(Long customerId) {
        return customerRepository.findById(customerId);
    }

}
