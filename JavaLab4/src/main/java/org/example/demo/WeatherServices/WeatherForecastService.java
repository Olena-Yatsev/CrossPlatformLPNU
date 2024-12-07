package org.example.demo.WeatherServices;

import org.example.demo.entities.WeatherData;
import org.example.demo.entities.WeatherForecast;
import org.example.demo.event.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WeatherForecastService implements WeatherStationEventListener, WeatherForecastEventPublisher {
    private final LinkedList<WeatherData> recentData = new LinkedList<>();
    private final List<WeatherForecastEventListener> listeners = new ArrayList<>();
    private ExecutorService executorService;
    private ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private int weatherDataMaxSize;

    public WeatherForecastService(int fixedThreadPoolSize, int weatherDataMaxSize) {
        this.executorService = Executors.newFixedThreadPool(fixedThreadPoolSize);
        this.weatherDataMaxSize = weatherDataMaxSize;
        this.scheduledExecutorService.scheduleAtFixedRate(this::notifySubscribers, 0, 5, TimeUnit.SECONDS);
    }

    public WeatherForecastService(ExecutorService executorService, int weatherDataMaxSize) {
        this.executorService = executorService;
        this.weatherDataMaxSize = weatherDataMaxSize;
    }

    public WeatherForecastService() {
        this.executorService = Executors.newFixedThreadPool(1);
        this.weatherDataMaxSize = 50;
        this.scheduledExecutorService.scheduleAtFixedRate(this::notifySubscribers, 0, 5, TimeUnit.SECONDS);

    }

    public void addWeatherData(WeatherData newData) {
        if (recentData.size() >= weatherDataMaxSize) {
            recentData.pollFirst();
        }
        recentData.add(newData);
    }

    public WeatherForecast updateForecast() {

        double predictedTemperature = predictValue(recentData.stream()
                .mapToDouble(WeatherData::temperatureInCelsius)
                .toArray());

        WeatherForecast weatherForecast = new WeatherForecast(predictedTemperature,
                LocalDateTime.now());
        return weatherForecast;
    }

    private double predictValue(double[] values) {
        if (values.length == 0) {
            return 0;
        }
        double trend = 0;
        for (int i = 1; i < values.length; i++) {
            trend += values[i] - values[i - 1];
        }
        trend /= values.length;

        return values[values.length - 1] + trend;
    }

    @Override
    public void update(WeatherStationEvent event) {
        System.out.println(event);
        executorService.execute(() -> addWeatherData(event.getData()));
    }

    @Override
    public void subscribe(WeatherForecastEventListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unsubscribe(WeatherForecastEventListener listener) {
        listeners.remove(listener);
    }

    @Override
    public void notifySubscribers() {
        if (recentData.isEmpty()) {
            return;
        }
        var event = new WeatherForecastEvent(this, updateForecast());
        for (var listener : listeners) {
            listener.update(event);
        }
    }

    public int getWeatherDataMaxSize() {
        return weatherDataMaxSize;
    }

    public void setWeatherDataMaxSize(int weatherDataMaxSize) {
        this.weatherDataMaxSize = weatherDataMaxSize;
    }

    public LinkedList<WeatherData> getRecentData() {
        return recentData;
    }

    public void setFixedThreadPoolSize(int fixedThreadPoolSize) {
        executorService.shutdown();
        this.executorService = Executors.newFixedThreadPool(fixedThreadPoolSize);
    }

}
