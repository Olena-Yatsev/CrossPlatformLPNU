package org.example.demo.event;

public interface WeatherStationEventPublisher {
    void subscribe(WeatherStationEventListener listener);
    void unsubscribe(WeatherStationEventListener listener);
    void notifySubscribers();
}
