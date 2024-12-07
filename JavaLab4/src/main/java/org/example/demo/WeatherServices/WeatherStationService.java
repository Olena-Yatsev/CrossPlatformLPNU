package org.example.demo.WeatherServices;

import org.example.demo.entities.WeatherStation;

import java.util.Map;
import java.util.concurrent.*;

public class WeatherStationService {
    private final WeatherForecastService weatherForecastService;
    private Map<WeatherStation, ScheduledFuture<?>> weatherStations = new ConcurrentHashMap<>();
    private ScheduledExecutorService scheduledExecutorService;

    public WeatherStationService(WeatherForecastService weatherForecastService, int scheduledThreadPoolSize) {
        this.weatherForecastService = weatherForecastService;
        this.scheduledExecutorService = Executors.newScheduledThreadPool(scheduledThreadPoolSize);
    }

    public WeatherStationService(WeatherForecastService weatherForecastService,
                                 ScheduledExecutorService scheduledExecutorService) {
        this.weatherForecastService = weatherForecastService;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    public void addWeatherStation(WeatherStation station, int periodInSeconds) {
        var future = scheduledExecutorService.scheduleAtFixedRate(station::notifySubscribers,
                0,
                periodInSeconds,
                TimeUnit.SECONDS);
        station.subscribe(weatherForecastService);
        weatherStations.put(station, future);
    }

    public void removeWeatherStation(WeatherStation station) {
        var future = weatherStations.remove(station);
        if(future != null){
            future.cancel(false);
        }
    }

    public void removeWeatherStationById(String stationId) {
        weatherStations.keySet().stream()
                .filter(station -> station.getId().toString().equals(stationId))
                .findFirst()
                .ifPresent(this::removeWeatherStation);
    }

    public void setScheduledThreadPoolSize(int scheduledThreadPoolSize){
        scheduledExecutorService.shutdown();
        this.scheduledExecutorService = Executors.newScheduledThreadPool(scheduledThreadPoolSize);
    }

    public Map<WeatherStation, ScheduledFuture<?>> getWeatherStations() {
        return weatherStations;
    }
}
