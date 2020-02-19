package com.wjltechservices.service;

import com.wjltechservices.database.model.Customer;
import com.wjltechservices.database.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

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

    public Optional<Customer> getCustomer(Long customerId) {
        return customerRepository.findById(customerId);
    }

}
