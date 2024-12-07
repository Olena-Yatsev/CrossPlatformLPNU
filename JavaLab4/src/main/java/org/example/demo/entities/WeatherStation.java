package org.example.demo.entities;

import org.example.demo.event.WeatherStationEvent;
import org.example.demo.event.WeatherStationEventListener;
import org.example.demo.event.WeatherStationEventPublisher;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class WeatherStation implements WeatherStationEventPublisher {
    private final List<WeatherStationEventListener> listeners = new ArrayList<>();
    private final UUID id = UUID.randomUUID();
    private String name;

    public WeatherStation(String name) {
        this.name = name;
    }

    public WeatherData collectData() {
        double temperature = Math.random() * 40 - 10;
        LocalDateTime timestamp = LocalDateTime.now();
        return new WeatherData(temperature, timestamp);
    }

    @Override
    public void subscribe(WeatherStationEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unsubscribe(WeatherStationEventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifySubscribers() {
        var event = new WeatherStationEvent(this, collectData());
        for (var listener : listeners) {
            listener.update(event);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WeatherStation that)) {
            return false;
        }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "WeatherStation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public List<WeatherStationEventListener> getListeners() {
        return listeners;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
