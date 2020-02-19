package com.wjltechservices.database.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vendor")
public class
Vendor {

    @Id
    @Column(name = "vendor_id", unique = true)
    private String vendorId;

    @Column(name = "vendor_name", unique = true)
    private String vendorName;

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    @Override
    public String toString() {
        return "Vendor{" +
                "vendorId='" + vendorId + '\'' +
                ", vendorName='" + vendorName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vendor vendor = (Vendor) o;

        if (vendorId != null ? !vendorId.equals(vendor.vendorId) : vendor.vendorId != null) return false;
        return vendorName != null ? vendorName.equals(vendor.vendorName) : vendor.vendorName == null;
    }

    @Override
    public int hashCode() {
        int result = vendorId != null ? vendorId.hashCode() : 0;
        result = 31 * result + (vendorName != null ? vendorName.hashCode() : 0);
        return result;
    }

    public static final class VendorBuilder {
        private String vendorId;
        private String vendorName;

        private VendorBuilder() {
        }

        public static VendorBuilder aVendor() {
            return new VendorBuilder();
        }

        public VendorBuilder withVendorId(String vendorId) {
            this.vendorId = vendorId;
            return this;
        }

        public VendorBuilder withVendorName(String vendorName) {
            this.vendorName = vendorName;
            return this;
        }

        public Vendor build() {
            Vendor vendor = new Vendor();
            vendor.setVendorId(vendorId);
            vendor.setVendorName(vendorName);
            return vendor;
        }
    }
}
