package com.wjltechservices.utilities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimeUtils {

    public static Long todayAtMidnightEpoch() {
        return ZonedDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT, ZoneId.of("UTC")).toEpochSecond();
    }

}
