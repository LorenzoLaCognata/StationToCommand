package stationtocommand.view.vehicleStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.View;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, List<Vehicle> vehicles) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Vehicles");
        for (Vehicle vehicle : vehicles) {
            showSidebar(breadCrumbBar, view, vehicle);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, Vehicle vehicle) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        showVehicleIcon(horizontalDetailsPane, vehicle);
        showVehicleButton(breadCrumbBar, view, horizontalDetailsPane, vehicle);
        showVehicleStatus(horizontalDetailsPane, vehicle);
    }

    private void showVehicleIcon(Pane pane, Vehicle vehicle) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, vehicle.getVehicleType().getResourcePath(), vehicle.getVehicleType().toString());
    }

    private void showVehicleButton(BreadCrumbBar<Object> breadCrumbBar, View view, Pane pane, Vehicle vehicle) {
        utilsView.addButtonToPane(pane, vehicle.toString(), (_ -> vehicleView.show(breadCrumbBar, view, vehicle)));
    }

    private void showVehicleStatus(Pane pane, Vehicle vehicle) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, vehicle.getVehicleStatus().getResourcePath(), vehicle.getVehicleStatus().toString());
    }

}
