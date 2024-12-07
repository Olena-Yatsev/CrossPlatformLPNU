package org.example.demo.entities;

import java.time.LocalDateTime;

public record WeatherForecast(double predictedTemperatureInCelsius,
                              LocalDateTime predictionTime) {
}
