package stationtocommand.view.vehicleStructure;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class VehicleView {

    public VehicleView() {
    }

    public void show(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

}
