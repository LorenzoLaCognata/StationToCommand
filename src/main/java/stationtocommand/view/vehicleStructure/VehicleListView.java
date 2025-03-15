package stationtocommand.view.vehicleStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class VehicleListView {

    private final UtilsView utilsView;
    private final VehicleView vehicleView;

    public VehicleListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.vehicleView = new VehicleView(utilsView);
    }

    public VehicleView getVehicleView() {
        return vehicleView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, List<Vehicle> vehicles) {
        utilsView.addSectionTitleLabel(navigationPanel, "Vehicles");
        for (Vehicle vehicle : vehicles) {
            showSidebar(breadCrumbBar, navigationPanel, vehicle);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Vehicle vehicle) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        showVehicleIcon(labelPane, vehicle);
        showVehicleButton(breadCrumbBar, navigationPanel, labelPane, vehicle);
        showVehicleStatus(labelPane, vehicle);
    }

    private void showVehicleIcon(Pane labelPane, Vehicle vehicle) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.BLANK, vehicle.getVehicleType().getResourcePath());
    }

    private void showVehicleButton(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane labelPane, Vehicle vehicle) {
        utilsView.addButtonToPane(labelPane, vehicle.toString(), (_ -> vehicleView.show(breadCrumbBar, navigationPanel, vehicle)));
    }

    private void showVehicleStatus(Pane labelPane, Vehicle vehicle) {
        utilsView.addIconToPane(labelPane, IconType.SMALL, IconColor.BLANK, vehicle.getVehicleStatus().getResourcePath());
    }

}
