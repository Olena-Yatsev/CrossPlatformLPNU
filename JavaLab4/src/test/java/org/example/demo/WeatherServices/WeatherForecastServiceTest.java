package org.example.demo.WeatherServices;

import org.example.demo.entities.WeatherData;
import org.example.demo.entities.WeatherForecast;
import org.example.demo.event.WeatherForecastEvent;
import org.example.demo.event.WeatherForecastEventListener;
import org.example.demo.event.WeatherStationEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class WeatherForecastServiceTest {
    private WeatherForecastService weatherForecastService;
    private ExecutorService executorService;

    @BeforeEach
    void setUp() {
        executorService = mock(ExecutorService.class);
        weatherForecastService = new WeatherForecastService(executorService, 10);
    }

    @Test
    void addWeatherData_NewData_AddedToRecentData() {
        WeatherData data = new WeatherData(25.0, LocalDateTime.now());
        weatherForecastService.addWeatherData(data);
        assertEquals(1, weatherForecastService.getRecentData().size());
    }

    @Test
    void updateForecast_WithRecentData_ReturnsCorrectForecast() {
        WeatherData data1 = new WeatherData(25.0,  LocalDateTime.now());
        WeatherData data2 = new WeatherData(26.0,  LocalDateTime.now().plusHours(1));
        WeatherForecast expectedForecast = new WeatherForecast(26.5,  null);
        weatherForecastService.addWeatherData(data1);
        weatherForecastService.addWeatherData(data2);

        WeatherForecast forecast = weatherForecastService.updateForecast();
        assertNotNull(forecast);
        assertEquals(expectedForecast.predictedTemperatureInCelsius(), forecast.predictedTemperatureInCelsius());
    }

    @Test
    void update_WithWeatherStationEvent_ExecutesRunnable() {
        WeatherData data = new WeatherData(25.0,  LocalDateTime.now());
        WeatherStationEvent event = new WeatherStationEvent(this, data);
        weatherForecastService.update(event);

        verify(executorService, times(1)).execute(any(Runnable.class));
    }

    @Test
    void subscribeAndNotifySubscribers_WithListener_ListenerNotified() {
        WeatherForecastEventListener listener = mock(WeatherForecastEventListener.class);
        weatherForecastService.subscribe(listener);

        WeatherData data = new WeatherData(25.0,  LocalDateTime.now());
        weatherForecastService.addWeatherData(data);
        weatherForecastService.notifySubscribers();

        verify(listener, times(1)).update(any(WeatherForecastEvent.class));
    }

    @Test
    void unsubscribe_WithListener_ListenerNotNotified() {
        WeatherForecastEventListener listener = mock(WeatherForecastEventListener.class);
        weatherForecastService.subscribe(listener);
        weatherForecastService.unsubscribe(listener);

        WeatherData data = new WeatherData(25.0,  LocalDateTime.now());
        weatherForecastService.addWeatherData(data);
        weatherForecastService.notifySubscribers();

        verify(listener, times(0)).update(any(WeatherForecastEvent.class));
    }
}