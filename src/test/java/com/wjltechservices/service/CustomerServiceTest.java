package com.wjltechservices.service;

import com.wjltechservices.database.model.Customer;
import com.wjltechservices.database.model.Customer.CustomerBuilder;
import com.wjltechservices.database.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private static final String CUSTOMER_NAME = "Joe Bloggs";
    private static final String HOME_CITY = "London";
    private static final Long CUSTOMER_ID = 1L;

    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;
    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        customerService = new CustomerService(customerRepository);

        testCustomer = CustomerBuilder.aCustomer()
                .withCustomerName(CUSTOMER_NAME)
                .withHomeCity(HOME_CITY).build();
    }

    @Test
    void testAddCustomer_persistsCustomerToDatabase() {
        customerService.addCustomer(CUSTOMER_NAME, HOME_CITY);

        verify(customerRepository).save(testCustomer);
    }

    @Test
    void testAddCustomer_throwsExceptionForNullCustomerName() {
        assertThrows(IllegalArgumentException.class, () -> customerService.addCustomer(null, HOME_CITY));
    }

    @Test
    void testAddCustomer_throwsExceptionForEmptyCustomerName() {
        assertThrows(IllegalArgumentException.class, () -> customerService.addCustomer("", HOME_CITY));
    }

    @Test
    void testAddCustomer_throwsExceptionForNullHomeCity() {
        assertThrows(IllegalArgumentException.class, () -> customerService.addCustomer(CUSTOMER_NAME, null));
    }

    @Test
    void testAddCustomer_throwsExceptionForEmptyHomeCity() {
        assertThrows(IllegalArgumentException.class, () -> customerService.addCustomer(CUSTOMER_NAME, ""));
    }

    @Test
    void testGetCustomer() {
        testCustomer.setCustomerId(CUSTOMER_ID);
        when(customerRepository.findById(CUSTOMER_ID)).thenReturn(Optional.of(testCustomer));

        Optional<Customer> customer = customerService.getCustomer(CUSTOMER_ID);
        assertTrue(customer.isPresent());
        assertThat(customer.get(), is(testCustomer));
    }


}