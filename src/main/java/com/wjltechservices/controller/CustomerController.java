package com.wjltechservices.controller;

import com.wjltechservices.database.model.Customer;
import com.wjltechservices.exception.CustomerNotFoundException;
import com.wjltechservices.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for customer endpoints
 */
@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    /**
     * Add a new customer to the system
     * <p>
     * Subsequent calls to the endpoint will be idempotent
     *
     * @param customerName Full name of the customer
     * @param homeCity     Home city of the customer
     * @return The id of the customer created
     */
    @PostMapping("/new")
    public Customer addCustomer(@RequestParam String customerName, @RequestParam String homeCity) {
        return customerService.addCustomer(customerName, homeCity);
    }

    /**
     * Return an existing customer
     * <p>
     * Will return 404 if no customer found for the provided ID
     *
     * @param customerId ID of the customer to return
     * @return The customer details, or 404 if no customer found
     */
    @GetMapping("/{customerId}")
    public Customer getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomer(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId));
    }
}
