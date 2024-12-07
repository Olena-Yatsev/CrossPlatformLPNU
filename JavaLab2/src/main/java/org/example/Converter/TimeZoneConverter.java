package org.example.Converter;

import org.example.Models.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class TimeZoneConverter {
    private ZoneId targetZoneId = ZoneId.of("Europe/London");

    public LocalTime[] convertToLondon(ZoneId userZoneId, LocalTime startTime, LocalTime endTime) {
        ZonedDateTime startZoned = startTime.atDate(LocalDate.now()).atZone(userZoneId);
        ZonedDateTime endZoned = endTime.atDate(LocalDate.now()).atZone(userZoneId);

        ZonedDateTime startLondon = startZoned.withZoneSameInstant(targetZoneId);
        ZonedDateTime endLondon = endZoned.withZoneSameInstant(targetZoneId);

        return new LocalTime[]{startLondon.toLocalTime(), endLondon.toLocalTime()};
    }

}