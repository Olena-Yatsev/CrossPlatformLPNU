package org.example.demo.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import org.example.demo.View.DataViewModel;
import org.example.demo.View.StationViewModel;
import org.example.demo.WeatherServices.WeatherForecastService;
import org.example.demo.WeatherServices.WeatherStationService;
import org.example.demo.entities.WeatherStation;
import org.example.demo.event.WeatherForecastEvent;
import org.example.demo.event.WeatherForecastEventListener;
import org.example.demo.event.WeatherStationEvent;
import org.example.demo.event.WeatherStationEventListener;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class WeatherAppController implements WeatherStationEventListener, WeatherForecastEventListener, Initializable {
    
    @FXML
    public TextField stationDataGenerationPeriodInput;
    @FXML
    public Button deleteStationButton;
    @FXML
    public Label aggregatedForecastOutput;
    @FXML
    private Button addStationButton;
    @FXML
    private Button applyThreadSettingsButton;
    @FXML
    private TextField fixedThreadPoolInput;
    @FXML
    private TextField scheduledThreadPoolInput;
    @FXML
    private TextField stationNameInput;
    
    @FXML
    private TableView<StationViewModel> stationTable;
    
    private DataViewModel dataViewModel = new DataViewModel();
    private WeatherStationService weatherStationService;
    private WeatherForecastService weatherForecastService;
    
    public WeatherAppController() {
        this.weatherForecastService = new WeatherForecastService();
        this.weatherStationService = new WeatherStationService(this.weatherForecastService, 1);
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fixedThreadPoolInput.addEventFilter(KeyEvent.KEY_TYPED, numericValidation(2));
        scheduledThreadPoolInput.addEventFilter(KeyEvent.KEY_TYPED, numericValidation(2));
        stationDataGenerationPeriodInput.addEventFilter(KeyEvent.KEY_TYPED, numericValidation(3));
        weatherForecastService.subscribe(this);
        
        TableColumn<StationViewModel, String> stationIdColumn = new TableColumn<>(" Station Id");
        TableColumn<StationViewModel, String> stationNameColumn = new TableColumn<>("Name");
        TableColumn<StationViewModel, String> TemperatureColumn = new TableColumn<>("Temperature, °C");
        TableColumn<StationViewModel, String> TimeColumn = new TableColumn<>("Time");
        
        stationIdColumn.setCellValueFactory(param -> param.getValue().stationIdProperty());
        stationNameColumn.setCellValueFactory(param -> param.getValue().stationNameProperty());
        TemperatureColumn.setCellValueFactory(param -> param.getValue().temperatureInCelsiusProperty());
        TimeColumn.setCellValueFactory(param -> param.getValue().timestampProperty());
        
        stationTable.getColumns()
                    .setAll(List.of(stationIdColumn,
                                    stationNameColumn,
                                    TemperatureColumn,
                                    TimeColumn));
        stationTable.setItems(dataViewModel.getStationViewModels());
    }

    @Override
    public void update(WeatherForecastEvent event) {
        Platform.runLater(() -> aggregatedForecastOutput.setText(
                "%.3f °C".formatted(event.getWeatherForecast().predictedTemperatureInCelsius())
        ));
    }

    @Override
    public void update(WeatherStationEvent event) {
        var data = dataViewModel.getById(((WeatherStation) event.getSource()).getId().toString());
        data.setTemperatureInCelsius(String.format("%.3f", event.getData().temperatureInCelsius()));
        data.setTimestamp(event.getData().timestamp().format(StationViewModel.formatter));
    }
    
    public void handleAddStationButtonClick(ActionEvent actionEvent) {
        if (stationNameInput.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Station name is required!");
            alert.show();
            return;
        }
        if (stationDataGenerationPeriodInput.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Station data generation period is required!");
            alert.show();
            return;
        }
        String stationName = stationNameInput.getText();
        int period = Integer.parseInt(stationDataGenerationPeriodInput.getText());
        
        WeatherStation station = new WeatherStation(stationName);
        station.subscribe(this);
        dataViewModel.getStationViewModels().add(new StationViewModel(station));
        weatherStationService.addWeatherStation(station, period);
    }
    
    public void handleApplyThreadSettingsButton(ActionEvent actionEvent) {
        if (fixedThreadPoolInput.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fixed thread pool size is required!");
            alert.show();
            return;
        }
        if (scheduledThreadPoolInput.getText().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Scheduled thread pool size is required!");
            alert.show();
            return;
        }
        int fixedThreadPoolSize = Integer.parseInt(fixedThreadPoolInput.getText());
        int scheduledThreadPoolSize = Integer.parseInt(scheduledThreadPoolInput.getText());
        
        weatherForecastService.setFixedThreadPoolSize(fixedThreadPoolSize);
        weatherStationService.setScheduledThreadPoolSize(scheduledThreadPoolSize);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Thread pool settings applied!", ButtonType.OK);
        alert.show();
    }
    
    
    private EventHandler<KeyEvent> numericValidation(final Integer maxLength) {
        return e -> {
            TextField txtTextField = (TextField) e.getSource();
            if (txtTextField.getText().length() >= maxLength) {
                e.consume();
            }
            if (!e.getCharacter().matches("[0-9]")) {
                e.consume();
            }
        };
    }
    
    public void handleDeleteStationButton(ActionEvent actionEvent) {
        var selected = stationTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            var stationId = selected.getStationId();
            weatherStationService.removeWeatherStationById(stationId);
            dataViewModel.removeById(stationId);
        }
    }
}
