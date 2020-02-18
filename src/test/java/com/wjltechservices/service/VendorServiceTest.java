package com.wjltechservices.service;

import com.wjltechservices.database.model.Vendor;
import com.wjltechservices.database.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendorServiceTest {

    public static final String TEST_VENDOR = "Test Vendor";
    public static final String TEST_VENDOR_ID = "testvendor";
    @Mock
    private VendorRepository vendorRepository;

    private VendorService vendorService;
    private Vendor testVendor;

    @BeforeEach
    void setUp() {
        vendorService = new VendorService(vendorRepository);

        testVendor = new Vendor();
        testVendor.setVendorName(TEST_VENDOR);
        testVendor.setVendorId(TEST_VENDOR_ID);
    }

    @Test
    void testAddVendor_generatesVendorId() {
        Vendor result = vendorService.addVendor(TEST_VENDOR);

        assertThat(result.getVendorId(), is(TEST_VENDOR_ID));
    }

    @Test
    void testAddVendor_doesWriteVendorToDatabase() {
        vendorService.addVendor(TEST_VENDOR);

        verify(vendorRepository).save(testVendor);
    }

    @Test
    void testGetVendor() {
        when(vendorRepository.findById(TEST_VENDOR_ID)).thenReturn(Optional.of(testVendor));

        Optional<Vendor> vendor = vendorService.getVendor(TEST_VENDOR_ID);
        assertTrue(vendor.isPresent());
        assertThat(vendor.get(), is(testVendor));
    }
}