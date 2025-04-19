package stationtocommand.view.organizationStructure;

import javafx.scene.Group;
import javafx.scene.Node;
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

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void addStationDetailsVehicle(View view, Group vehicleIcons, Node vehicleNode) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addVehicleIcon(horizontalDetailsPane);
        addVehicleButton(view, horizontalDetailsPane, vehicleIcons, vehicleNode);
        addVehicleStatusIcon(horizontalDetailsPane);
    }

    private void addVehicleTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, vehicle.getVehicleType());
        utilsView.addMainTitleLabel(horizontalTitlePane, vehicle.toString());
    }

    private void addVehicleIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, vehicle.getVehicleType());
    }

    private void addVehicleButton(View view, Pane pane, Group vehicleIcons, Node vehicleNode) {
        utilsView.addButtonToPane(pane, vehicle.toString(), (_ -> showVehicle(view, vehicleIcons, vehicleNode)));
    }

    private void addVehicleStatusIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, vehicle.getVehicleStatus());
    }

    public void showVehicle(View view, Group vehicleIcons, Node vehicleNode) {
        View.viewRunnable = () -> showVehicle(view, vehicleIcons, vehicleNode);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), vehicle);
        view.getNavigationPanel().clearAll();
        showVehicleDetails(view);
        showVehicleMap(view, vehicleIcons, vehicleNode);
    }

    private void showVehicleDetails(View view) {
        addVehicleTitle(view);
    }

    private void showVehicleMap(View view, Group vehicleIcons, Node vehicleNode) {
        for (Node vehicleIcon : vehicleIcons.getChildren()) {
            vehicleIcon.setVisible(vehicleIcon.equals(vehicleNode));
        }
    }

}
