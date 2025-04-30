package stationtocommand.view.organizationStructure;

import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.mainStructure.ViewWithNode;

public class VehicleView extends ViewWithNode {

    private final Vehicle vehicle;
    private final UtilsView utilsView;

    // Constructor

    public VehicleView(Vehicle vehicle, UtilsView utilsView) {
        super(utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, vehicle.getVehicleType(), vehicle.getLocation()));
        this.vehicle = vehicle;
        this.utilsView = utilsView;
    }


    // Getter

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void addListDetails(View view) {
        utilsView.addIconAndButtonAndIcon(view.getDetailsPane(), vehicle.getVehicleType(), vehicle.toString(), (_ -> show(view)), vehicle.getVehicleStatus());
    }


    // Methods

    public void show(View view) {
        View.viewRunnable = () -> show(view);
        showNavigationPanel(view);
        showMap(view);
    }

    private void showNavigationPanel(View view) {
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), vehicle);
        view.clearNavigationPanel();
        utilsView.addIconAndTitle(view.getTitlePane(), vehicle.getVehicleType(), vehicle.toString());
    }

    private void showMap(View view) {
        view.hideMap();
        showNode();
    }

}