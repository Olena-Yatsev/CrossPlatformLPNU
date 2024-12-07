package org.example.demo.event;

public interface WeatherForecastEventPublisher {
    void subscribe(WeatherForecastEventListener listener);
    void unsubscribe(WeatherForecastEventListener listener);
    void notifySubscribers();
}
