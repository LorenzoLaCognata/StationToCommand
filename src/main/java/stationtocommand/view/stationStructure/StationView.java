package stationtocommand.view.stationStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.responderStructure.ResponderListView;
import stationtocommand.view.unitStructure.UnitListView;
import stationtocommand.view.vehicleStructure.VehicleListView;

import java.util.List;

public class StationView {

    private final UnitListView unitListView;
    private final ResponderListView responderListView;
    private final VehicleListView vehicleListView;

    public StationView(UtilsView utilsView) {
        this.unitListView = new UnitListView(utilsView);
        this.responderListView = new ResponderListView(utilsView);
        this.vehicleListView = new VehicleListView(utilsView);
    }

    public void show(Pane pane, List<Node> nodes, Station station) {
        unitListView.show(pane, nodes, station.getUnits());
        responderListView.show(pane, nodes, station.getResponders());
        vehicleListView.show(pane, nodes, station.getVehicles());
    }

}
