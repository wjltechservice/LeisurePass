package com.wjltechservices.controller;

import com.wjltechservices.database.model.Vendor;
import com.wjltechservices.exceptions.VendorNotFoundException;
import com.wjltechservices.service.VendorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendorControllerTest {
    public static final String TEST_VENDOR = "Test Vendor";
    public static final String TEST_VENDOR_ID = "testvendor";

    @Mock
    private VendorService vendorService;

    private VendorController vendorController;

    @BeforeEach
    void setUp() {
        vendorController = new VendorController(vendorService);
    }

    @Test
    void testAddVendor() {
        vendorController.addVendor(TEST_VENDOR);

        verify(vendorService).addVendor(TEST_VENDOR);
    }

    @Test
    void testGetVendor() {
        Vendor testVendor = new Vendor();
        testVendor.setVendorId(TEST_VENDOR_ID);
        testVendor.setVendorName(TEST_VENDOR);
        when(vendorService.getVendor(TEST_VENDOR_ID)).thenReturn(Optional.of(testVendor));

        Vendor result = vendorController.getVendor(TEST_VENDOR_ID);

        verify(vendorService).getVendor(TEST_VENDOR_ID);
        assertThat(result, is(testVendor));
    }

    @Test
    void testGetVendor_throwsExceptionIfNoVendor() {
        when(vendorService.getVendor(TEST_VENDOR_ID)).thenReturn(Optional.empty());

        assertThrows(VendorNotFoundException.class, () -> vendorController.getVendor(TEST_VENDOR_ID));
    }
}