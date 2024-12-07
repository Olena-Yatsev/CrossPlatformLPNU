package org.example.UserScheduler;

import org.example.Models.Availability;
import org.example.Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MeetingSchedulerTest {

    private MeetingScheduler meetingScheduler;

    @BeforeEach
    void setUp() {
        List<User> users = new ArrayList<>();

        // User 1: New York Time Zone
        User user1 = new User("User 1", ZoneId.of("America/New_York"));
        user1.Add_Availability(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(11, 0));
        user1.Add_Availability(DayOfWeek.TUESDAY, LocalTime.of(10, 0), LocalTime.of(12, 0));

        // User 2: London Time Zone
        User user2 = new User("User 2", ZoneId.of("Europe/London"));
        user2.Add_Availability(DayOfWeek.MONDAY, LocalTime.of(9, 30), LocalTime.of(10, 30));
        user2.Add_Availability(DayOfWeek.WEDNESDAY, LocalTime.of(10, 0), LocalTime.of(12, 0));

        users.add(user1);
        users.add(user2);

        meetingScheduler = new MeetingScheduler(users);
    }

    @Test
    void testFindBestTimeForMeeting() {
        // Test for finding the best time for a meeting (duration = 60 minutes)
        String result = meetingScheduler.findBestTimeForMeeting(60);
        assertTrue(result.contains("Optimal meeting found"), "There should be an optimal meeting time found");
    }

    @Test
    void testFindBestTimeForMeeting_NoSuitableTime() {
        // Test for finding the best time for a meeting where no suitable time is available (duration = 180 minutes)
        String result = meetingScheduler.findBestTimeForMeeting(180);
        assertEquals("No suitable meeting time found.", result, "The result should indicate that no suitable time was found.");
    }

    @Test
    void testCalculateLongestCommonAvailability_NoCommonTime() {
        // Test for calculating longest common availability when no common time exists
        User user1 = new User("User 1", ZoneId.of("America/New_York"));
        user1.Add_Availability(DayOfWeek.MONDAY, LocalTime.of(9, 0), LocalTime.of(10, 0));
        User user2 = new User("User 2", ZoneId.of("Europe/London"));
        user2.Add_Availability(DayOfWeek.MONDAY, LocalTime.of(18, 0), LocalTime.of(19, 0));

        meetingScheduler = new MeetingScheduler(new ArrayList<>(Arrays.asList(user1, user2)));

        Map<DayOfWeek, Long> commonAvailability = meetingScheduler.calculateLongestCommonAvailability(new ArrayList<>(Arrays.asList(user1, user2)));

        // Monday should not have common availability between the two users
        assertTrue(commonAvailability.isEmpty(), "There should be no common availability between the users.");
    }

    @Test
    void testCalculateLongestCommonAvailability_EmptyAvailability() {
        // Test for calculating longest common availability with no availability data
        User user1 = new User("User 1", ZoneId.of("America/New_York"));
        User user2 = new User("User 2", ZoneId.of("Europe/London"));

        meetingScheduler = new MeetingScheduler(new ArrayList<>(Arrays.asList(user1, user2)));

        Map<DayOfWeek, Long> commonAvailability = meetingScheduler.calculateLongestCommonAvailability(new ArrayList<>(Arrays.asList(user1, user2)));

        assertTrue(commonAvailability.isEmpty(), "If no availability exists, the common availability map should be empty.");
    }
}
