package org.example.demo.View;

import javafx.beans.property.SimpleStringProperty;
import org.example.demo.entities.WeatherStation;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class StationViewModel {
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
    private SimpleStringProperty stationId;
    private SimpleStringProperty stationName;
    private SimpleStringProperty temperatureInCelsius;
    private SimpleStringProperty timestamp;

    public StationViewModel(SimpleStringProperty stationId,
                            SimpleStringProperty stationName,
                            SimpleStringProperty temperatureInCelsius,
                            SimpleStringProperty timestamp) {
        this.stationId = stationId;
        this.stationName = stationName;
        this.temperatureInCelsius = temperatureInCelsius;
        this.timestamp = timestamp;
    }

    public StationViewModel(WeatherStation station) {
        this.stationId = new SimpleStringProperty(station.getId().toString());
        this.stationName = new SimpleStringProperty(station.getName());
        this.temperatureInCelsius = new SimpleStringProperty();
        this.timestamp = new SimpleStringProperty();
    }

    public SimpleStringProperty stationIdProperty() {
        return stationId;
    }

    public SimpleStringProperty stationNameProperty() {
        return stationName;
    }

    public SimpleStringProperty temperatureInCelsiusProperty() {
        return temperatureInCelsius;
    }

    public SimpleStringProperty timestampProperty() {
        return timestamp;
    }

    public String getStationId() {
        return stationId.get();
    }

    public void setStationId(String stationId) {
        this.stationId.set(stationId);
    }

    public String getStationName() {
        return stationName.get();
    }

    public void setStationName(String stationName) {
        this.stationName.set(stationName);
    }

    public String getTemperatureInCelsius() {
        return temperatureInCelsius.get();
    }

    public void setTemperatureInCelsius(String temperatureInCelsius) {
        this.temperatureInCelsius.set(temperatureInCelsius);
    }

    public String getTimestamp() {
        return timestamp.get();
    }

    public void setTimestamp(String timestamp) {
        this.timestamp.set(timestamp);
    }
}
