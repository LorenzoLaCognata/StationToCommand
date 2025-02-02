package view.missionStructure.missionModule;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class MissionVehicleView {

    public MissionVehicleView() {
    }

    public void show(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

}
