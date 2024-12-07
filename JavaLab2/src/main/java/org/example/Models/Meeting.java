package org.example.Models;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Meeting {
    int duration;
    DayOfWeek dayOfWeek;
    LocalTime startTime;

    public Meeting(int duration, DayOfWeek dayOfWeek, LocalTime startTime) {
        this.duration = duration;
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
    }

    public int getDuration() { return duration; }
    public DayOfWeek getDayOfWeek() { return dayOfWeek; }
    public LocalTime getStartTime() { return startTime; }

    @Override
    public String toString() {
        return "Meeting: " + this.dayOfWeek + ", Start: " + this.startTime
                + ", Duration: " + this.duration + " hours (London Time)";
    }
}
