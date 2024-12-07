package org.example.demo.event;

import java.util.EventListener;

public interface WeatherForecastEventListener extends EventListener {
    void update(WeatherForecastEvent event);
}
