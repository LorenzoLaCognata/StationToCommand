package stationtocommand.view.vehicleStructure;

import javafx.scene.control.Button;
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
        utilsView.addLabel(pane, "Vehicles");
        for (Vehicle vehicle : vehicles) {
            Button button = new Button(vehicle.toString());
            button.setOnAction(_ -> vehicleView.show(breadCrumbBar, pane, vehicle));
            pane.getChildren().add(button);
        }
    }

}
