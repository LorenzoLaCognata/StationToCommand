package view.stationStructure.stationListModule;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.stationStructure.stationModule.Station;
import view.stationStructure.stationModule.StationView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class StationListView {

    // TODO: temporary
    private static int counter = 0;
    private final UtilsView utilsView;
    private final StationView stationView;

    public StationListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.stationView = new StationView(utilsView);
    }

    public void show(Pane pane1, Pane pane2, List<Node> nodes1, List<Node> nodes2, List<Station> stations) {
        Label stationsSeparator = new Label("----------------------\nStations");
        pane1.getChildren().addAll(stationsSeparator);

        for (Station station : stations) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Node> nextNodes1 = utilsView.resetAndAddToPane(pane1, nodes1, button);
                stationView.show(pane1, nextNodes1, station);
            });
            utilsView.addToSidebar(pane1, button, station.toString(), station.getUnits().size() + " units");

            Circle circle = new Circle(100+counter, 100, 12, Color.BLUE);
            utilsView.addToMap(pane2, circle);
        }
        this.counter = this.counter + 25;
    }


}
