package org.example.demo.View;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class DataViewModel {
    private final ObservableList<StationViewModel> stationViewModels = FXCollections.observableList(new ArrayList<>());

    public DataViewModel() {
    }

    public StationViewModel getById(String id) {
        return stationViewModels.stream()
                .filter(m -> m.getStationId().equals(id))
                .findAny()
                .orElseThrow();
    }

    public void removeById(String stationId) {
        stationViewModels.removeIf(m -> m.getStationId().equals(stationId));
    }

    public ObservableList<StationViewModel> getStationViewModels() {
        return stationViewModels;
    }
}
