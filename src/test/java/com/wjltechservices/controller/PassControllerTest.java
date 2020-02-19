package com.wjltechservices.controller;

import com.wjltechservices.service.PassService;
import com.wjltechservices.utilities.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PassControllerTest {

    private static final String VENDOR_ID = "vendor";
    private static final long CUSTOMER_ID = 1L;
    private static final String PASS_CITY = "London";
    private static final int ONE_DAY = 1;
    private static final String PASS_ID = "testPassId";

    @Mock
    private PassService passService;

    private PassController passController;

    @BeforeEach
    void setUp() {
        passController = new PassController(passService);
    }

    @Test
    void testAddPass() {
        Long todayAtMidnight = TimeUtils.todayAtMidnightEpoch();
        passController.addPass(VENDOR_ID, CUSTOMER_ID, PASS_CITY, todayAtMidnight, ONE_DAY);

        verify(passService).addPass(VENDOR_ID, CUSTOMER_ID, PASS_CITY, todayAtMidnight, ONE_DAY);
    }

    @Test
    void testRenewPass() {
        passController.renewPass(PASS_ID, CUSTOMER_ID);

        verify(passService).renewPass(PASS_ID, CUSTOMER_ID);
    }

    @Test
    void testValidatePass() {
        passController.validatePass(PASS_ID, VENDOR_ID);

        verify(passService).validatePass(PASS_ID, VENDOR_ID);
    }

    @Test
    void testCancelPass() {
        passController.cancelPass(PASS_ID, CUSTOMER_ID);

        verify(passService).cancelPass(PASS_ID, CUSTOMER_ID);
    }
}