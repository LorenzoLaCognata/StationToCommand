package stationtocommand.view.organizationStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class VehicleView {

    private final Vehicle vehicle;
    private final UtilsView utilsView;

    public VehicleView(Vehicle vehicle, UtilsView utilsView) {
        this.vehicle = vehicle;
        this.utilsView = utilsView;
    }

    public void addStationDetailsVehicle(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addVehicleIcon(horizontalDetailsPane);
        addVehicleButton(view, horizontalDetailsPane);
        addVehicleStatusIcon(horizontalDetailsPane);
    }

    private void addVehicleIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, vehicle.getVehicleType());
    }

    private void addVehicleButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, vehicle.toString(), (_ -> showVehicle(view)));
    }

    private void addVehicleStatusIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, vehicle.getVehicleStatus());
    }

    public void showVehicle(View view) {
        View.viewRunnable = () -> showVehicle(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), vehicle);
        view.getNavigationPanel().clearAll();
        showVehicleDetails(view);
    }

    private void showVehicleDetails(View view) {
        addVehicleTitle(view);
    }

    private void addVehicleTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, vehicle.getVehicleType());
        utilsView.addMainTitleLabel(horizontalTitlePane, vehicle.toString());
    }

}
