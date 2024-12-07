package org.example.Converter;

import org.example.Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.*;

class TimeZoneConverterTest {

    private TimeZoneConverter timeZoneConverter;

    @BeforeEach
    void setUp() {
        timeZoneConverter = new TimeZoneConverter();
    }

    @Test
    void testConvertToLondon_SameTimeZone() {
        // Test where the user is already in the Europe/London zone
        ZoneId userZoneId = ZoneId.of("Europe/London");
        LocalTime startTime = LocalTime.of(10, 0); // 10:00 AM
        LocalTime endTime = LocalTime.of(12, 0);   // 12:00 PM

        LocalTime[] convertedTimes = timeZoneConverter.convertToLondon(userZoneId, startTime, endTime);

        assertEquals(startTime, convertedTimes[0], "Start time should be the same when already in London time zone.");
        assertEquals(endTime, convertedTimes[1], "End time should be the same when already in London time zone.");
    }

    @Test
    void testConvertToLondon_TimeZoneDifference() {
        // Test where the user is in a different time zone (e.g., New York)
        ZoneId userZoneId = ZoneId.of("America/New_York");
        LocalTime startTime = LocalTime.of(10, 0); // 10:00 AM (New York)
        LocalTime endTime = LocalTime.of(12, 0);   // 12:00 PM (New York)

        LocalTime[] convertedTimes = timeZoneConverter.convertToLondon(userZoneId, startTime, endTime);

        // New York is 5 hours behind London, so expect the converted time to be 15:00 and 17:00 in London
        assertEquals(LocalTime.of(15, 0), convertedTimes[0], "Start time should be converted to London time.");
        assertEquals(LocalTime.of(17, 0), convertedTimes[1], "End time should be converted to London time.");
    }

    @Test
    void testConvertToLondon_BoundaryTest() {
        // Test with a start time just before midnight and an end time just after midnight in a different time zone
        ZoneId userZoneId = ZoneId.of("America/Los_Angeles");
        LocalTime startTime = LocalTime.of(23, 30); // 11:30 PM (Los Angeles)
        LocalTime endTime = LocalTime.of(1, 0);     // 1:00 AM (next day in Los Angeles)

        LocalTime[] convertedTimes = timeZoneConverter.convertToLondon(userZoneId, startTime, endTime);

        // Los Angeles is 8 hours behind London, so:
        // Start time: 23:30 Los Angeles => 07:30 London
        // End time: 01:00 Los Angeles => 09:00 London
        assertEquals(LocalTime.of(7, 30), convertedTimes[0], "Start time should be converted to London time.");
        assertEquals(LocalTime.of(9, 0), convertedTimes[1], "End time should be converted to London time.");
    }

    @Test
    void testConvertToLondon_SameStartAndEndTime() {
        // Test where start and end time are the same in the user's zone
        ZoneId userZoneId = ZoneId.of("Europe/Berlin");
        LocalTime startTime = LocalTime.of(10, 0); // 10:00 AM (Berlin)
        LocalTime endTime = startTime;            // Same end time as start time

        LocalTime[] convertedTimes = timeZoneConverter.convertToLondon(userZoneId, startTime, endTime);

        // Berlin is 1 hour ahead of London, so expect the converted time to be 09:00 London
        assertEquals(LocalTime.of(9, 0), convertedTimes[0], "Start time should be converted to London time.");
        assertEquals(LocalTime.of(9, 0), convertedTimes[1], "End time should be converted to London time.");
    }

}
