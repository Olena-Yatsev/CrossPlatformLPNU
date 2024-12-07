package org.example.demo.WeatherServices;

import org.example.demo.entities.WeatherStation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class WeatherStationServiceTest {
    private WeatherStationService weatherStationService;
    private WeatherForecastService weatherForecastService;
    private ScheduledExecutorService scheduledExecutorService;
    private WeatherStation weatherStation;
    private ScheduledFuture<?> scheduledFuture;

    WeatherStationServiceTest() {
    }

    @BeforeEach
    void setUp() {
        this.weatherForecastService = (WeatherForecastService) Mockito.mock(WeatherForecastService.class);
        this.scheduledExecutorService = (ScheduledExecutorService)Mockito.mock(ScheduledExecutorService.class);
        this.weatherStationService = new WeatherStationService(this.weatherForecastService, this.scheduledExecutorService);
        this.weatherStation = (WeatherStation)Mockito.mock(WeatherStation.class);
        this.scheduledFuture = (ScheduledFuture)Mockito.mock(ScheduledFuture.class);
    }

    @Test
    void addWeatherStation_ValidStation_StationAdded() {
        Mockito.when(this.scheduledExecutorService.scheduleAtFixedRate((Runnable)Mockito.any(Runnable.class), Mockito.anyLong(), Mockito.anyLong(), (TimeUnit)Mockito.any(TimeUnit.class))).then((i) -> {
            return this.scheduledFuture;
        });
        this.weatherStationService.addWeatherStation(this.weatherStation, 10);
        ((WeatherStation)Mockito.verify(this.weatherStation, Mockito.times(1))).subscribe(this.weatherForecastService);
        Assertions.assertTrue(this.weatherStationService.getWeatherStations().containsKey(this.weatherStation));
    }

    @Test
    void removeWeatherStation_ExistingStation_StationRemoved() {
        Mockito.when(this.scheduledExecutorService.scheduleAtFixedRate((Runnable)Mockito.any(Runnable.class), Mockito.anyLong(), Mockito.anyLong(), (TimeUnit)Mockito.any(TimeUnit.class))).then((i) -> {
            return this.scheduledFuture;
        });
        this.weatherStationService.addWeatherStation(this.weatherStation, 10);
        this.weatherStationService.removeWeatherStation(this.weatherStation);
        ((ScheduledFuture)Mockito.verify(this.scheduledFuture, Mockito.times(1))).cancel(false);
        Assertions.assertFalse(this.weatherStationService.getWeatherStations().containsKey(this.weatherStation));
    }

    @Test
    void removeWeatherStationById_ExistingStationId_StationRemoved() {
        UUID id = UUID.randomUUID();
        Mockito.when(this.weatherStation.getId()).thenReturn(id);
        Mockito.when(this.scheduledExecutorService.scheduleAtFixedRate((Runnable)Mockito.any(Runnable.class), Mockito.anyLong(), Mockito.anyLong(), (TimeUnit)Mockito.any(TimeUnit.class))).then((i) -> {
            return this.scheduledFuture;
        });
        this.weatherStationService.addWeatherStation(this.weatherStation, 10);
        this.weatherStationService.removeWeatherStationById(id.toString());
        ((ScheduledFuture)Mockito.verify(this.scheduledFuture, Mockito.times(1))).cancel(false);
        Assertions.assertFalse(this.weatherStationService.getWeatherStations().containsKey(this.weatherStation));
    }

    @Test
    void setScheduledThreadPoolSize_NewSize_ThreadPoolResized() {
        this.weatherStationService.setScheduledThreadPoolSize(10);
        ((ScheduledExecutorService)Mockito.verify(this.scheduledExecutorService, Mockito.times(1))).shutdown();
    }
}