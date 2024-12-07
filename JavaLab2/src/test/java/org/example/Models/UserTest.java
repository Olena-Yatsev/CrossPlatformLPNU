package org.example.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private User user;

    @BeforeEach
    void setUp() {
        user = new User("John Doe", ZoneId.of("America/New_York"));
    }

    @Test
    void testConstructorWithNameAndZone() {
        assertNotNull(user);
        assertEquals("John Doe", user.get_name());
        assertEquals(ZoneId.of("America/New_York"), user.get_zone_id());
    }

    @Test
    void testConstructorWithNameZoneAndAvailability() {
        Map<DayOfWeek, List<Availability>> availabilityMap = user.get_availability();
        assertNotNull(availabilityMap);
        assertTrue(availabilityMap.isEmpty());
    }

    @Test
    void testAddAvailability() {
        // Add availability for Monday
        LocalTime start = LocalTime.of(9, 0);
        LocalTime end = LocalTime.of(17, 0);
        user.Add_Availability(DayOfWeek.MONDAY, start, end);

        // Validate the availability was added
        Map<DayOfWeek, List<Availability>> availabilityMap = user.get_availability();
        assertTrue(availabilityMap.containsKey(DayOfWeek.MONDAY));
        assertEquals(1, availabilityMap.get(DayOfWeek.MONDAY).size());
        Availability addedAvailability = availabilityMap.get(DayOfWeek.MONDAY).get(0);
        assertEquals(start, addedAvailability.get_start_time());
        assertEquals(end, addedAvailability.get_end_time());
    }

    @Test
    void testMultipleAvailabilityForSameDay() {
        LocalTime start1 = LocalTime.of(9, 0);
        LocalTime end1 = LocalTime.of(12, 0);
        LocalTime start2 = LocalTime.of(13, 0);
        LocalTime end2 = LocalTime.of(17, 0);

        // Add two availabilities for Monday
        user.Add_Availability(DayOfWeek.MONDAY, start1, end1);
        user.Add_Availability(DayOfWeek.MONDAY, start2, end2);

        Map<DayOfWeek, List<Availability>> availabilityMap = user.get_availability();
        assertEquals(2, availabilityMap.get(DayOfWeek.MONDAY).size());
        Availability firstAvailability = availabilityMap.get(DayOfWeek.MONDAY).get(0);
        Availability secondAvailability = availabilityMap.get(DayOfWeek.MONDAY).get(1);

        assertEquals(start1, firstAvailability.get_start_time());
        assertEquals(end1, firstAvailability.get_end_time());
        assertEquals(start2, secondAvailability.get_start_time());
        assertEquals(end2, secondAvailability.get_end_time());
    }


    @Test
    void testSetName() {
        user.set_name("Jane Doe");
        assertEquals("Jane Doe", user.get_name());
    }

    @Test
    void testSetZoneId() {
        ZoneId newZone = ZoneId.of("Europe/London");
        user.set_zone_id(newZone);
        assertEquals(newZone, user.get_zone_id());
    }
}
