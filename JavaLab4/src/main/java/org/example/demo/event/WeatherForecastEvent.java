package org.example.demo.event;


import org.example.demo.entities.WeatherForecast;

public class WeatherForecastEvent extends WeatherEvent {
    private final WeatherForecast weatherForecast;
    
    public WeatherForecastEvent(Object source, WeatherForecast weatherForecast) {
        super(source);
        this.weatherForecast = weatherForecast;
    }
    
    public WeatherForecast getWeatherForecast() {
        return weatherForecast;
    }
    
    @Override
    public String toString() {
        return "WeatherForecastEvent{" +
                       "weatherForecast=" + weatherForecast +
                       '}';
    }
}
