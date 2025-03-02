package stationtocommand.view.vehicleStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class VehicleView {

    private final UtilsView utilsView;

    public VehicleView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Vehicle vehicle) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, vehicle);
        utilsView.addBreadCrumb(breadCrumbBar, vehicle);
        utilsView.clearPane(navigationPanel);
        showSidebar(navigationPanel, vehicle);
    }

    private void showSidebar(Pane navigationPanel, Vehicle vehicle) {
        showVehicleDetails(navigationPanel, vehicle);
    }

    private void showVehicleDetails(Pane navigationPanel, Vehicle vehicle) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, utilsView.vehicleIconPath(vehicle.getVehicleType()));
        utilsView.addMainTitleLabel(labelPane, vehicle.toString());
    }

}
