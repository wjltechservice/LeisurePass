package com.wjltechservices.controller;

import com.wjltechservices.database.model.Customer;
import com.wjltechservices.exceptions.CustomerNotFoundException;
import com.wjltechservices.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    private static final String TEST_CUSTOMER = "Joe Bloggs";
    private static final Long TEST_CUSTOMER_ID = 1L;
    private static final String TEST_HOME_CITY = "London";

    @Mock
    private CustomerService customerService;

    private CustomerController customerController;

    @BeforeEach
    void setUp() {
        customerController = new CustomerController(customerService);
    }

    @Test
    void testAddCustomer() {
        customerController.addCustomer(TEST_CUSTOMER, TEST_HOME_CITY);

        verify(customerService).addCustomer(TEST_CUSTOMER, TEST_HOME_CITY);
    }

    @Test
    void testGetCustomer() {
        Customer testCustomer = new Customer();
        testCustomer.setCustomerId(TEST_CUSTOMER_ID);
        testCustomer.setCustomerName(TEST_CUSTOMER);
        testCustomer.setHomeCity(TEST_HOME_CITY);
        when(customerService.getCustomer(TEST_CUSTOMER_ID)).thenReturn(Optional.of(testCustomer));

        Customer result = customerController.getCustomer(TEST_CUSTOMER_ID);

        verify(customerService).getCustomer(TEST_CUSTOMER_ID);
        assertThat(result, is(testCustomer));
    }

    @Test
    void testGetCustomer_throwsExceptionIfNoCustomer() {
        when(customerService.getCustomer(TEST_CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerController.getCustomer(TEST_CUSTOMER_ID));
    }

}