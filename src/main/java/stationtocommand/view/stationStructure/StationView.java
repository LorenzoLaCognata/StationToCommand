package stationtocommand.view.stationStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.responderStructure.ResponderListView;
import stationtocommand.view.unitStructure.UnitListView;
import stationtocommand.view.vehicleStructure.VehicleListView;

public class StationView {

    private final UtilsView utilsView;
    private final UnitListView unitListView;
    private final ResponderListView responderListView;
    private final VehicleListView vehicleListView;

    public StationView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.unitListView = new UnitListView(utilsView);
        this.responderListView = new ResponderListView(utilsView);
        this.vehicleListView = new VehicleListView(utilsView);
    }

    public UnitListView getUnitListView() {
        return unitListView;
    }

    public ResponderListView getResponderListView() {
        return responderListView;
    }

    public VehicleListView getVehicleListView() {
        return vehicleListView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Station station) {
        utilsView.addBreadCrumb(breadCrumbBar, station);
        utilsView.clearPane(pane);
        unitListView.show(breadCrumbBar, pane, station.getUnits());
        responderListView.show(breadCrumbBar, pane, station.getResponders());
        vehicleListView.show(breadCrumbBar, pane, station.getVehicles());
    }

}
