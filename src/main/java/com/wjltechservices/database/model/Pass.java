package com.wjltechservices.database.model;

import javax.persistence.*;

@Entity
public class Pass {

    @Id
    @Column(name = "pass_id")
    private String passId;

    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(name = "pass_city")
    private String passCity;

    @Column(name = "valid_from")
    private Long validFrom;

    @Column(name = "duration_days")
    private Integer durationDays;

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getPassCity() {
        return passCity;
    }

    public void setPassCity(String passCity) {
        this.passCity = passCity;
    }

    public Long getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Long validFrom) {
        this.validFrom = validFrom;
    }

    public Integer getDurationDays() {
        return durationDays;
    }

    public void setDurationDays(Integer durationDays) {
        this.durationDays = durationDays;
    }

    @Override
    public String toString() {
        return "Pass{" +
                "passId='" + passId + '\'' +
                ", vendor=" + vendor +
                ", customer=" + customer +
                ", passCity='" + passCity + '\'' +
                ", validFrom=" + validFrom +
                ", durationDays=" + durationDays +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pass pass = (Pass) o;

        if (passId != null ? !passId.equals(pass.passId) : pass.passId != null) return false;
        if (vendor != null ? !vendor.equals(pass.vendor) : pass.vendor != null) return false;
        if (customer != null ? !customer.equals(pass.customer) : pass.customer != null) return false;
        if (passCity != null ? !passCity.equals(pass.passCity) : pass.passCity != null) return false;
        if (validFrom != null ? !validFrom.equals(pass.validFrom) : pass.validFrom != null) return false;
        return durationDays != null ? durationDays.equals(pass.durationDays) : pass.durationDays == null;
    }

    @Override
    public int hashCode() {
        int result = passId != null ? passId.hashCode() : 0;
        result = 31 * result + (vendor != null ? vendor.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (passCity != null ? passCity.hashCode() : 0);
        result = 31 * result + (validFrom != null ? validFrom.hashCode() : 0);
        result = 31 * result + (durationDays != null ? durationDays.hashCode() : 0);
        return result;
    }

    public static final class PassBuilder {
        private String passId;
        private Vendor vendor;
        private Customer customer;
        private String passCity;
        private Long validFrom;
        private Integer durationDays;

        private PassBuilder() {
        }

        public static PassBuilder aPass() {
            return new PassBuilder();
        }

        public PassBuilder withPassId(String passId) {
            this.passId = passId;
            return this;
        }

        public PassBuilder withVendor(Vendor vendor) {
            this.vendor = vendor;
            return this;
        }

        public PassBuilder withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public PassBuilder withPassCity(String passCity) {
            this.passCity = passCity;
            return this;
        }

        public PassBuilder withValidFrom(Long validFrom) {
            this.validFrom = validFrom;
            return this;
        }

        public PassBuilder withDurationDays(Integer durationDays) {
            this.durationDays = durationDays;
            return this;
        }

        public Pass build() {
            Pass pass = new Pass();
            pass.setPassId(passId);
            pass.setVendor(vendor);
            pass.setCustomer(customer);
            pass.setPassCity(passCity);
            pass.setValidFrom(validFrom);
            pass.setDurationDays(durationDays);
            return pass;
        }
    }
}
