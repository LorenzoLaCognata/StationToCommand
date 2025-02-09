package stationtocommand.view.vehicleStructure;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class VehicleListView {

    private final UtilsView utilsView;
    private final VehicleView vehicleView;

    public VehicleListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.vehicleView = new VehicleView();
    }

    public void show(Pane pane, List<Node> nodes, List<Vehicle> vehicles) {
        Label vehiclesSeparator = new Label("----------------------\nVehicles");
        pane.getChildren().addAll(vehiclesSeparator);

        for (Vehicle vehicle : vehicles) {
            Button button = new Button();
            button.setOnAction(_ -> {
                utilsView.resetAndAddToPane(pane, nodes, button);
                vehicleView.show(pane);
            });
            String integrity = String.format("%.0f%%", vehicle.getIntegrity() * 100);
            String condition = String.format("%.0f%%", vehicle.getCondition() * 100);
            utilsView.addToSidebar(pane, button, vehicle.toString(), "Integrity: " + integrity + " - Condition: " + condition);
        }
    }

}
