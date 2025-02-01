package view.stationStructure.stationModule;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import model.stationStructure.stationModule.Station;
import view.responderStructure.responderListModule.ResponderListView;
import view.unitStructure.unitListModule.UnitListView;
import view.utilsStructure.utilsModule.UtilsView;
import view.vehicleStructure.vehicleListModule.VehicleListView;

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

    public void show(Pane pane, List<Control> controls, Station station) {
        unitListView.show(pane, controls, station.getUnits());
        responderListView.show(pane, controls, station.getResponders());
        vehicleListView.show(pane, controls, station.getVehicles());
    }

}
