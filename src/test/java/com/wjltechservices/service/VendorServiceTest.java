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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class VendorServiceTest {

    private static final String TEST_VENDOR = "Test Vendor";
    private static final String TEST_VENDOR_ID = "testvendor";

    @Mock
    private VendorRepository vendorRepository;

    private VendorService vendorService;
    private Vendor testVendor;

    @BeforeEach
    void setUp() {
        testVendor = Vendor.VendorBuilder.aVendor()
                .withVendorId(TEST_VENDOR_ID)
                .withVendorName(TEST_VENDOR)
                .build();
        vendorService = new VendorService(vendorRepository);
    }

    @Test
    void testAddVendor_doesWriteVendorToDatabase() {
        vendorService.addVendor(TEST_VENDOR);

        verify(vendorRepository).save(testVendor);
    }

    @Test
    void testAddVendor_throwsExceptionForNullVendor() {
        assertThrows(IllegalArgumentException.class, () -> vendorService.addVendor(null));
    }

    @Test
    void testAddVendor_throwsExceptionForEmptyVendor() {
        assertThrows(IllegalArgumentException.class, () -> vendorService.addVendor(""));
    }

    @Test
    void testGetVendor() {
        when(vendorRepository.findById(TEST_VENDOR_ID)).thenReturn(Optional.of(testVendor));

        Optional<Vendor> vendor = vendorService.getVendor(TEST_VENDOR_ID);
        assertTrue(vendor.isPresent());
        assertThat(vendor.get(), is(testVendor));
    }
}