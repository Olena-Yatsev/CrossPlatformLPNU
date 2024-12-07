package org.example.Models;

import java.time.LocalTime;

public class Availability{
    private LocalTime _start_time;
    private LocalTime _end_time;

    public Availability(LocalTime start, LocalTime end){
        _start_time = start;
        _end_time = end;
    }

    public LocalTime get_start_time() {
        return _start_time;
    }

    public LocalTime get_end_time() {
        return _end_time;
    }

    @Override
    public String toString() {
        return "Available from " + _start_time + " to " + _end_time;
    }
}
