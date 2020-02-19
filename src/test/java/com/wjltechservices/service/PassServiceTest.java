package com.wjltechservices.service;

import com.wjltechservices.database.model.Customer;
import com.wjltechservices.database.model.Customer.CustomerBuilder;
import com.wjltechservices.database.model.Pass;
import com.wjltechservices.database.model.Vendor;
import com.wjltechservices.database.model.Vendor.VendorBuilder;
import com.wjltechservices.database.repository.PassRepository;
import com.wjltechservices.exception.PassNotFoundException;
import com.wjltechservices.service.dto.Validation;
import com.wjltechservices.utilities.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PassServiceTest {

    public static final int TWO_DAYS_SECONDS = 86400 * 2;
    private static final String PASS_ID = "passid";
    private static final String VENDOR_ID = "vendorid";
    private static final Long CUSTOMER_ID = 1L;
    private static final String PASS_CITY = "London";
    private static final Integer ONE_DAY = 1;

    @Mock
    private PassRepository passRepository;
    @Mock
    private PassIdGenerator passIdGenerator;
    @Mock
    private VendorService vendorService;
    @Mock
    private CustomerService customerService;

    private PassService passService;
    private Pass validTestPass;
    private Pass expiredTestPass;
    private Vendor testVendor;
    private Customer testCustomer;

    private static Stream<Arguments> provideBadArgumentsForAddPass() {
        return Stream.of(
                Arguments.of(null, CUSTOMER_ID, TimeUtils.todayAtMidnightEpoch(), ONE_DAY),
                Arguments.of("", CUSTOMER_ID, TimeUtils.todayAtMidnightEpoch(), ONE_DAY),
                Arguments.of(VENDOR_ID, null, TimeUtils.todayAtMidnightEpoch(), ONE_DAY),
                Arguments.of(VENDOR_ID, CUSTOMER_ID, null, ONE_DAY),
                Arguments.of(VENDOR_ID, CUSTOMER_ID, -1L, ONE_DAY),
                Arguments.of(VENDOR_ID, CUSTOMER_ID, TimeUtils.todayAtMidnightEpoch(), null),
                Arguments.of(VENDOR_ID, CUSTOMER_ID, TimeUtils.todayAtMidnightEpoch(), -1)
        );
    }

    @BeforeEach
    void setUp() {
        testVendor = VendorBuilder.aVendor().withVendorId(VENDOR_ID).build();
        testCustomer = CustomerBuilder.aCustomer().withCustomerId(CUSTOMER_ID).build();
        validTestPass = Pass.PassBuilder.aPass()
                .withPassId(PASS_ID)
                .withVendor(testVendor)
                .withCustomer(testCustomer)
                .withPassCity(PASS_CITY)
                .withValidFrom(TimeUtils.todayAtMidnightEpoch())
                .withDurationDays(ONE_DAY)
                .build();

        Long twoDaysAgo = TimeUtils.todayAtMidnightEpoch() - TWO_DAYS_SECONDS;
        expiredTestPass = Pass.PassBuilder.aPass()
                .withPassId(PASS_ID)
                .withVendor(VendorBuilder.aVendor().withVendorId(VENDOR_ID).build())
                .withCustomer(CustomerBuilder.aCustomer().withCustomerId(CUSTOMER_ID).build())
                .withPassCity(PASS_CITY)
                .withValidFrom(twoDaysAgo)
                .withDurationDays(ONE_DAY)
                .build();

        passService = new PassService(passRepository, vendorService, customerService, passIdGenerator);
    }

    @Test
    void testAddPass_persistsToDatabase() {
        when(vendorService.getVendor(VENDOR_ID)).thenReturn(Optional.of(testVendor));
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(Optional.of(testCustomer));
        when(passIdGenerator.generateId(VENDOR_ID, CUSTOMER_ID)).thenReturn(PASS_ID);
        passService.addPass(VENDOR_ID, CUSTOMER_ID, PASS_CITY, TimeUtils.todayAtMidnightEpoch(), ONE_DAY);

        verify(passRepository).save(validTestPass);
    }

    @Test
    void testAddPass_getsComplexPassId() {
        when(vendorService.getVendor(VENDOR_ID)).thenReturn(Optional.of(testVendor));
        when(customerService.getCustomer(CUSTOMER_ID)).thenReturn(Optional.of(testCustomer));
        passService.addPass(VENDOR_ID, CUSTOMER_ID, PASS_CITY, TimeUtils.todayAtMidnightEpoch(), ONE_DAY);

        verify(passIdGenerator).generateId(VENDOR_ID, CUSTOMER_ID);
    }

    @ParameterizedTest
    @MethodSource("provideBadArgumentsForAddPass")
    void testAddPass_ThrowsExceptionForBadParams(String vendorId, Long customerId, Long validFrom, Integer duration) {
        assertThrows(IllegalArgumentException.class, () -> passService.addPass(vendorId, customerId, PASS_CITY, validFrom, duration));
    }

    @Test
    void testRenewPass_updatesValidFromTime() {
        when(passRepository.findPassByPassIdAndCustomerId(PASS_ID, CUSTOMER_ID)).thenReturn(Optional.of(expiredTestPass));

        passService.renewPass(PASS_ID, CUSTOMER_ID);

        verify(passRepository).save(validTestPass);
    }

    @Test
    void testRenewPass_throwsNotFoundException() {
        when(passRepository.findPassByPassIdAndCustomerId(PASS_ID, CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(PassNotFoundException.class, () -> passService.renewPass(PASS_ID, CUSTOMER_ID));
    }

    @Test
    void testValidatePass_returnsTrueWhenPassIsValid() {
        when(passRepository.findPassByPassIdAndVendorId(PASS_ID, VENDOR_ID)).thenReturn(Optional.of(validTestPass));

        Validation result = passService.validatePass(PASS_ID, VENDOR_ID);

        assertThat(result.isValid(), is(true));
    }

    @Test
    void testValidatePass_returnsFalseWhenPassIsInvalid() {
        when(passRepository.findPassByPassIdAndVendorId(PASS_ID, VENDOR_ID)).thenReturn(Optional.of(expiredTestPass));

        Validation result = passService.validatePass(PASS_ID, VENDOR_ID);

        assertThat(result.isValid(), is(false));
    }

    @Test
    void testValidatePass_throwsNotFoundException() {
        when(passRepository.findPassByPassIdAndVendorId(PASS_ID, VENDOR_ID)).thenReturn(Optional.empty());

        assertThrows(PassNotFoundException.class, () -> passService.validatePass(PASS_ID, VENDOR_ID));
    }

    @Test
    void testCancelPass_removesPassFromDatabase() {
        when(passRepository.findPassByPassIdAndCustomerId(PASS_ID, CUSTOMER_ID)).thenReturn(Optional.of(validTestPass));

        passService.cancelPass(PASS_ID, CUSTOMER_ID);

        verify(passRepository).delete(validTestPass);
    }

    @Test
    void testCancelPass_throwsNotFoundException() {
        when(passRepository.findPassByPassIdAndCustomerId(PASS_ID, CUSTOMER_ID)).thenReturn(Optional.empty());

        assertThrows(PassNotFoundException.class, () -> passService.cancelPass(PASS_ID, CUSTOMER_ID));
    }
}