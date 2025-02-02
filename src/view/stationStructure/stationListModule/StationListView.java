package view.stationStructure.stationListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
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

    public void show(Pane sidebar, Pane map, List<Control> sidebarNodes, List<Control> mapNodes, List<Station> stations) {
        System.out.println("6: "+ map.getChildren().size());
        Label stationsSeparator = new Label("----------------------\nStations");
        sidebar.getChildren().addAll(stationsSeparator);

        for (Station station : stations) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Control> nextSidebarNodes = utilsView.setPane(sidebar, sidebarNodes, button);
                stationView.show(sidebar, nextSidebarNodes, station);
            });
            utilsView.addToSidebar(sidebar, button, station.toString(), station.getUnits().size() + " units");

            Circle circle = new Circle(100+counter, 100, 12, Color.BLUE);
            utilsView.addToMap(map, circle);
        }
        this.counter = this.counter + 25;
    }


}
