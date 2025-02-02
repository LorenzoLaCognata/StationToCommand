package view.missionStructure.missionListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.missionStructure.missionModule.Mission;
import model.vehicleStructure.vehicleModule.Vehicle;
import view.missionStructure.missionModule.MissionVehicleView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class MissionVehicleListView {

    private final UtilsView utilsView;
    private final MissionVehicleView missionVehicleView;

    public MissionVehicleListView(UtilsView utilsView, Mission mission) {
        this.utilsView = utilsView;
        this.missionVehicleView = new MissionVehicleView();
    }

    public void show(Pane pane, List<Control> controls, Mission mission, List<Vehicle> vehicles) {
        Label vehiclesSeparator = new Label("----------------------\nVehicles");
        pane.getChildren().addAll(vehiclesSeparator);

        for (Vehicle vehicle : vehicles) {
            Button button = new Button();
            button.setOnAction(_ -> {
                utilsView.setPane(pane, controls, button);
                missionVehicleView.show(pane);
            });
            String integrity = String.format("%.0f%%", vehicle.getIntegrity() * 100);
            String condition = String.format("%.0f%%", vehicle.getCondition() * 100);
            utilsView.addToSidebar(pane, button, vehicle.toString(), "Integrity: " + integrity + " - Condition: " + condition);
        }
    }

}
