package org.example.UserScheduler;

import org.example.Converter.TimeZoneConverter;
import org.example.IScheduler;
import org.example.Models.Availability;
import org.example.Models.User;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.*;

public class MeetingScheduler implements IScheduler {

    private final List<User> users;

    public MeetingScheduler(List<User> users) {
        this.users = users;
    }

    @Override
    public String findBestTimeForMeeting(int durationInMinutes) {
        Map<DayOfWeek, List<Availability>> commonAvailability = findCommonAvailability();
        StringBuilder result = new StringBuilder();

        for (Map.Entry<DayOfWeek, List<Availability>> entry : commonAvailability.entrySet()) {
            DayOfWeek day = entry.getKey();
            List<Availability> availabilities = entry.getValue();

            for (Availability availability : availabilities) {
                LocalTime start = availability.get_start_time();
                LocalTime end = availability.get_end_time();

                if (isMeetingPossible(start, end, durationInMinutes)) {
                    result.append("Optimal meeting found: ").append(day)
                            .append(", Start: ").append(start)
                            .append(", Duration: ").append(durationInMinutes)
                            .append(" minutes (London Time)\n");
                }
            }
        }

        return result.length() > 0 ? result.toString() : "No suitable meeting time found.";
    }

    private Map<DayOfWeek, List<Availability>> findCommonAvailability() {
        Map<DayOfWeek, List<Availability>> commonAvailability = new HashMap<>();
        TimeZoneConverter timeZoneConverter = new TimeZoneConverter();

        for (User user : users) {
            Map<DayOfWeek, List<Availability>> userAvailability = user.get_availability();
            ZoneId userZone = user.get_zone_id();

            for (Map.Entry<DayOfWeek, List<Availability>> entry : userAvailability.entrySet()) {
                DayOfWeek day = entry.getKey();
                List<Availability> londonAvailabilities = new ArrayList<>();

                for (Availability availability : entry.getValue()) {
                    LocalTime[] londonTimes = timeZoneConverter.convertToLondon(userZone, availability.get_start_time(), availability.get_end_time());
                    londonAvailabilities.add(new Availability(londonTimes[0], londonTimes[1]));
                }

                commonAvailability.merge(day, londonAvailabilities, this::mergeAvailability);
            }
        }
        return commonAvailability;
    }

    private List<Availability> mergeAvailability(List<Availability> list1, List<Availability> list2) {
        List<Availability> merged = new ArrayList<>();
        for (Availability a1 : list1) {
            for (Availability a2 : list2) {
                if (a1.get_start_time().isBefore(a2.get_end_time()) && a1.get_end_time().isAfter(a2.get_start_time())) {
                    merged.add(new Availability(
                            a1.get_start_time().isAfter(a2.get_start_time()) ? a1.get_start_time() : a2.get_start_time(),
                            a1.get_end_time().isBefore(a2.get_end_time()) ? a1.get_end_time() : a2.get_end_time()));
                }
            }
        }
        return merged;
    }

    private boolean isMeetingPossible(LocalTime start, LocalTime end, int minDurationInMinutes) {
        return Duration.between(start, end).toMinutes() >= minDurationInMinutes;
    }

    @Override
    public Map<DayOfWeek, Long> calculateLongestCommonAvailability(List<User> users) {
        Map<DayOfWeek, List<Availability>> dayToAvailabilityMap = findCommonAvailability();
        Map<DayOfWeek, Long> longestCommonTimes = new HashMap<>();

        for (Map.Entry<DayOfWeek, List<Availability>> entry : dayToAvailabilityMap.entrySet()) {
            DayOfWeek day = entry.getKey();
            List<Availability> availabilities = entry.getValue();

            if (availabilities.isEmpty()) continue;

            LocalTime maxStartTime = availabilities.get(0).get_start_time();
            LocalTime minEndTime = availabilities.get(0).get_end_time();

            for (Availability availability : availabilities) {
                maxStartTime = maxStartTime.isBefore(availability.get_start_time()) ? availability.get_start_time() : maxStartTime;
                minEndTime = minEndTime.isAfter(availability.get_end_time()) ? availability.get_end_time() : minEndTime;
            }

            long duration = maxStartTime.isBefore(minEndTime) ? Duration.between(maxStartTime, minEndTime).toMinutes() : 0L;
            longestCommonTimes.put(day, duration);
        }

        return longestCommonTimes;
    }
}
