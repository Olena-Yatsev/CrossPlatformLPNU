package org.example.demo.event;

import java.util.EventListener;

public interface WeatherStationEventListener extends EventListener {
    void update(WeatherStationEvent event);
}
