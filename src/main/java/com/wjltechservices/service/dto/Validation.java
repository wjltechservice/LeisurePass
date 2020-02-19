package com.wjltechservices.service.dto;

/**
 * DTO for pass validation operation
 */
public class Validation {

    private String passId;
    private String vendorId;
    private boolean isValid;

    public Validation(String passId, String vendorId, boolean isValid) {
        this.passId = passId;
        this.vendorId = vendorId;
        this.isValid = isValid;
    }

    public String getPassId() {
        return passId;
    }

    public void setPassId(String passId) {
        this.passId = passId;
    }

    public String getVendorId() {
        return vendorId;
    }

    public void setVendorId(String vendorId) {
        this.vendorId = vendorId;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    @Override
    public String toString() {
        return "Validation{" +
                "passId='" + passId + '\'' +
                ", vendorId='" + vendorId + '\'' +
                ", isValid=" + isValid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Validation that = (Validation) o;

        if (isValid != that.isValid) return false;
        if (passId != null ? !passId.equals(that.passId) : that.passId != null) return false;
        return vendorId != null ? vendorId.equals(that.vendorId) : that.vendorId == null;
    }

    @Override
    public int hashCode() {
        int result = passId != null ? passId.hashCode() : 0;
        result = 31 * result + (vendorId != null ? vendorId.hashCode() : 0);
        result = 31 * result + (isValid ? 1 : 0);
        return result;
    }
}
