package com.wjltechservices.service;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

/**
 * Generator of complex IDs for passes
 * <p>
 * This generator must create IDs of sufficient complexity so as to make the chance of collisions even in a scaled
 * system so remote as to be effectively impossible
 */
@Component
public class PassIdGenerator {

    /**
     * Generate a complex passID
     *
     * @param vendorId   ID of the vendor providing the attraction
     * @param customerId ID of the customer purchasing the pass
     * @return Complex PassID which is effectively unique
     */
    public String generateId(String vendorId, Long customerId) {
        Long epochMillis = Instant.now().toEpochMilli();
        String uuid = UUID.randomUUID().toString();

        return String.format("%d-%s-%d-%s", epochMillis, vendorId, customerId, uuid);
    }
}
