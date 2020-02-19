package com.wjltechservices.database.model;

import javax.persistence.*;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue
    @Column(name = "customer_id", unique = true)
    private Long customerId;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "home_city")
    private String homeCity;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", homeCity='" + homeCity + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (customerId != null ? !customerId.equals(customer.customerId) : customer.customerId != null) return false;
        if (customerName != null ? !customerName.equals(customer.customerName) : customer.customerName != null)
            return false;
        return homeCity != null ? homeCity.equals(customer.homeCity) : customer.homeCity == null;
    }

    @Override
    public int hashCode() {
        int result = customerId != null ? customerId.hashCode() : 0;
        result = 31 * result + (customerName != null ? customerName.hashCode() : 0);
        result = 31 * result + (homeCity != null ? homeCity.hashCode() : 0);
        return result;
    }

    public static final class CustomerBuilder {
        private Long customerId;
        private String customerName;
        private String homeCity;

        private CustomerBuilder() {
        }

        public static CustomerBuilder aCustomer() {
            return new CustomerBuilder();
        }

        public CustomerBuilder withCustomerId(Long customerId) {
            this.customerId = customerId;
            return this;
        }

        public CustomerBuilder withCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public CustomerBuilder withHomeCity(String homeCity) {
            this.homeCity = homeCity;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer();
            customer.setCustomerId(customerId);
            customer.setCustomerName(customerName);
            customer.setHomeCity(homeCity);
            return customer;
        }
    }
}
