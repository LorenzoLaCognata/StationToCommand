package view.missionStructure.missionListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.missionStructure.missionModule.Mission;
import model.stationStructure.stationModule.Station;
import view.missionStructure.missionModule.MissionStationView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class MissionStationListView {

    private final UtilsView utilsView;
    private final MissionStationView missionStationView;

    public MissionStationListView(UtilsView utilsView, Mission mission) {
        this.utilsView = utilsView;
        this.missionStationView = new MissionStationView(utilsView, mission);
    }

    public void show(Pane pane, List<Control> controls, Mission mission, List<Station> stations) {
        Label stationsSeparator = new Label("----------------------\nStations");
        pane.getChildren().addAll(stationsSeparator);

        for (Station station : stations) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Control> newControls = utilsView.setPane(pane, controls, button);
                missionStationView.show(pane, newControls, mission, station);
            });
            utilsView.addToSidebar(pane, button, station.toString(), "");
        }
    }


}
