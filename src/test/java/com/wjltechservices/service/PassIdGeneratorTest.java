package com.wjltechservices.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.matchesPattern;

class PassIdGeneratorTest {

    private PassIdGenerator passIdGenerator;

    @BeforeEach
    void setUp() {
        passIdGenerator = new PassIdGenerator();
    }

    @Test
    void testGenerateId() {
        // Ensure that the ID generated is of sufficient complexity to make collision effectively impossible
        String id = passIdGenerator.generateId("testvendor", 34L);

        // pattern is creationEpochMillis-customerId-vendorId-JavaUUID
        assertThat(id, matchesPattern("([0-9]*){13}-testvendor-34-([a-f0-9]{8}(-[a-f0-9]{4}){4}[a-f0-9]{8})"));
    }
}