package view.stationStructure.stationListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.stationStructure.stationModule.Station;
import view.stationStructure.stationModule.StationView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class StationListView {

    private final UtilsView utilsView;
    private final StationView stationView;

    public StationListView(UtilsView utilsView, StationView stationView) {
        this.utilsView = utilsView;
        this.stationView = stationView;
    }

    public void show(Pane pane, List<Control> controls, List<Station> stations) {
        Label stationsSeparator = new Label("----------------------\nStations");
        pane.getChildren().addAll(stationsSeparator);

        for (Station station : stations) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Control> newControls = utilsView.setBreadcrumbs(pane, controls, button);
                stationView.show(pane, newControls, station);
            });
            utilsView.addPaneEntry(pane, button, station.toString(), station.getUnits().size() + " units");
        }
    }


}
