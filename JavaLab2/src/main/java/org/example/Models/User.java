package org.example.Models;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class User{
    private String _name;
    private ZoneId _zone_id;
    private Map<DayOfWeek, List<Availability>> _availability;

    public User(){

    }
    public User(String name, ZoneId zone){
        _name = name;
        _zone_id = zone;
        _availability = new HashMap<>();
    }
    public User(String name, ZoneId zone, Map<DayOfWeek, List<Availability>> availability){
        _name = name;
        _zone_id = zone;
        _availability = availability;
    }

    public void Add_Availability(DayOfWeek day, LocalTime start, LocalTime end) {
        Availability newAvailability = new Availability(start, end);
        List<Availability> checkAvailabilityDay = _availability.get(day);

        if (checkAvailabilityDay == null) {
            checkAvailabilityDay = new ArrayList<>();
            _availability.put(day, checkAvailabilityDay);
        }
        checkAvailabilityDay.add(newAvailability);
    }
    public String get_name(){
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public void set_zone_id(ZoneId _zone_id) {
        this._zone_id = _zone_id;
    }

    public ZoneId get_zone_id() {
        return _zone_id;
    }

    public Map<DayOfWeek, List<Availability>> get_availability() {
        return _availability;
    }

    @Override
    public String toString() {
        return "Name: " + _name + ", \nZone: " + _zone_id + ", \nAvailability: " + _availability;
    }
}
