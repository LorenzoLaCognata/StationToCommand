package stationtocommand.view.vehicleStructure;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

public class VehicleView {

    private final UtilsView utilsView;

    public VehicleView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Vehicle vehicle) {
        View.viewRunnable = () -> show(breadCrumbBar, pane, vehicle);
        utilsView.addBreadCrumb(breadCrumbBar, vehicle);
        utilsView.clearPane(pane);
        showSidebar(pane, vehicle);
    }

    private void showSidebar(Pane pane, Vehicle vehicle) {
        showVehicleDetails(pane, vehicle);
    }

    private void showVehicleDetails(Pane pane1, Vehicle vehicle) {
        HBox hBox = new HBox(10);
        ImageView imageView = utilsView.mediumIcon(utilsView.vehicleIconPath(vehicle.getVehicleType()));
        utilsView.addNodeToPane(hBox, imageView);
        utilsView.addMainTitleLabel(hBox, vehicle.toString());
        utilsView.addNodeToPane(pane1, hBox);
    }

}
