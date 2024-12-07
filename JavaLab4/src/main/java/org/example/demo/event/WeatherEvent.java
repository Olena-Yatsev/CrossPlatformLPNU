package org.example.demo.event;

import java.util.EventObject;

public abstract class WeatherEvent extends EventObject {
    public WeatherEvent(Object source) {
        super(source);
    }
}
