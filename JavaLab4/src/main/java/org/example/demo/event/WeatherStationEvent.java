package org.example.demo.event;


import org.example.demo.entities.WeatherData;

public class WeatherStationEvent extends WeatherEvent {
    private final WeatherData data;
    
    public WeatherStationEvent(Object source, WeatherData data) {
        super(source);
        this.data = data;
    }
    
    public WeatherData getData() {
        return data;
    }
    
    @Override
    public String toString() {
        return "WeatherStationEvent{" +
                       "data=" + data +
                       ", source=" + source +
                       '}';
    }
}
