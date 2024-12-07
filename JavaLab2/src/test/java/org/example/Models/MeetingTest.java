package org.example.Models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class MeetingTest {

    private Meeting meeting;

    @BeforeEach
    void setUp() {
        // Initialize the Meeting object before each test
        meeting = new Meeting(2, DayOfWeek.MONDAY, LocalTime.of(10, 0));
    }

    @Test
    void testConstructor() {
        assertNotNull(meeting);
        assertEquals(2, meeting.getDuration());
        assertEquals(DayOfWeek.MONDAY, meeting.getDayOfWeek());
        assertEquals(LocalTime.of(10, 0), meeting.getStartTime());
    }

    @Test
    void testGetDuration() {
        assertEquals(2, meeting.getDuration());
    }

    @Test
    void testGetDayOfWeek() {
        assertEquals(DayOfWeek.MONDAY, meeting.getDayOfWeek());
    }

    @Test
    void testGetStartTime() {
        assertEquals(LocalTime.of(10, 0), meeting.getStartTime());
    }

    @Test
    void testToString() {
        String expectedString = "Meeting: MONDAY, Start: 10:00, Duration: 2 hours (London Time)";
        assertEquals(expectedString, meeting.toString());
    }

    @Test
    void testMeetingWithDifferentDuration() {
        Meeting newMeeting = new Meeting(1, DayOfWeek.WEDNESDAY, LocalTime.of(14, 30));
        assertEquals(1, newMeeting.getDuration());
        assertEquals(DayOfWeek.WEDNESDAY, newMeeting.getDayOfWeek());
        assertEquals(LocalTime.of(14, 30), newMeeting.getStartTime());
    }


}
