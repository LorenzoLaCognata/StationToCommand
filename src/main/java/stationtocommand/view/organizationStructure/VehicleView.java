package stationtocommand.view.organizationStructure;

import javafx.scene.Node;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class VehicleView {

    private final Vehicle vehicle;
    private final Node node;
    private final UtilsView utilsView;

    public VehicleView(Vehicle vehicle, UtilsView utilsView) {
        this.vehicle = vehicle;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, vehicle.getVehicleType(), vehicle.getLocation());
        this.utilsView = utilsView;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public Node getNode() {
        return node;
    }

    public void showNode() {
        node.setVisible(true);
    }

    public void addListDetails(View view) {
        utilsView.addIconAndButtonAndIcon(view.getDetailsPane(), vehicle.getVehicleType(), vehicle.toString(), (_ -> show(view)), vehicle.getVehicleStatus());
    }

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
        view.addToMap(view.getDispatchView().getMissionViews().values().stream().toList().getFirst().getNode());
    }

}