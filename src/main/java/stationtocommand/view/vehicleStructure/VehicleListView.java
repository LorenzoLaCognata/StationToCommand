package stationtocommand.view.vehicleStructure;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.vehicleStructure.Vehicle;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, List<Vehicle> vehicles) {
        utilsView.addSectionTitleLabel(pane, "Vehicles");
        for (Vehicle vehicle : vehicles) {
            showSidebar(breadCrumbBar, pane, vehicle);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Vehicle vehicle) {
        HBox hBox = new HBox(10);
        pane.getChildren().add(hBox);

        showVehicleIcon(vehicle, hBox);
        showVehicleButton(breadCrumbBar, pane, vehicle, hBox);
    }

    private void showVehicleIcon(Vehicle vehicle, HBox hBox) {
        ImageView imageView = utilsView.smallIcon(utilsView.vehicleIconPath(vehicle.getVehicleType()));
        hBox.getChildren().add(imageView);
    }

    private void showVehicleButton(BreadCrumbBar<Object> breadCrumbBar, Pane pane, Vehicle vehicle, HBox hBox) {
        Button button = new Button(vehicle.toString());
        button.setOnAction(_ -> vehicleView.show(breadCrumbBar, pane, vehicle));
        hBox.getChildren().add(button);
    }

}
