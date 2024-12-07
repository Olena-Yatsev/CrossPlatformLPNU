package org.example.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class AvailabilityTest {

    private Availability availability;

    @BeforeEach
    void setUp() {
        // Initialize an Availability object before each test
        availability = new Availability(LocalTime.of(9, 0), LocalTime.of(17, 0));
    }

    @Test
    void testConstructor() {
        assertNotNull(availability);
        assertEquals(LocalTime.of(9, 0), availability.get_start_time());
        assertEquals(LocalTime.of(17, 0), availability.get_end_time());
    }

    @Test
    void testGetStartTime() {
        assertEquals(LocalTime.of(9, 0), availability.get_start_time());
    }

    @Test
    void testGetEndTime() {
        assertEquals(LocalTime.of(17, 0), availability.get_end_time());
    }

    @Test
    void testToString() {
        String expectedString = "Available from 09:00 to 17:00";
        assertEquals(expectedString, availability.toString());
    }

    @Test
    void testAvailabilityWithDifferentTimes() {
        Availability newAvailability = new Availability(LocalTime.of(8, 30), LocalTime.of(12, 30));
        assertEquals(LocalTime.of(8, 30), newAvailability.get_start_time());
        assertEquals(LocalTime.of(12, 30), newAvailability.get_end_time());
    }


}
