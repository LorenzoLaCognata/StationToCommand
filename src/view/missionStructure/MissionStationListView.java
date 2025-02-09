package view.missionStructure;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.missionStructure.Mission;
import model.stationStructure.Station;
import view.mainStructure.UtilsView;

import java.util.List;

public class MissionStationListView {

    private final UtilsView utilsView;
    private final MissionStationView missionStationView;

    public MissionStationListView(UtilsView utilsView, Mission mission) {
        this.utilsView = utilsView;
        this.missionStationView = new MissionStationView(utilsView, mission);
    }

    public void show(Pane pane, List<Node> nodes, Mission mission, List<Station> stations) {
        Label stationsSeparator = new Label("----------------------\nStations");
        pane.getChildren().addAll(stationsSeparator);

        for (Station station : stations) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Node> nextNodes = utilsView.resetAndAddToPane(pane, nodes, button);
                missionStationView.show(pane, nextNodes, mission, station);
            });
            utilsView.addToSidebar(pane, button, station.toString(), "");
        }
    }


}
