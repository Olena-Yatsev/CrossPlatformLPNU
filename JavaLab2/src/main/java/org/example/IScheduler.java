package org.example;

import org.example.Models.User;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

public interface IScheduler {
    public String findBestTimeForMeeting(int durationInMinutes);
    public Map<DayOfWeek, Long> calculateLongestCommonAvailability(List<User> users);
}
