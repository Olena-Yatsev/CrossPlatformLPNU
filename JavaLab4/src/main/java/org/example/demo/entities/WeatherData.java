package org.example.demo.entities;

import java.time.LocalDateTime;

public record WeatherData(double temperatureInCelsius,
                          LocalDateTime timestamp) {
}
