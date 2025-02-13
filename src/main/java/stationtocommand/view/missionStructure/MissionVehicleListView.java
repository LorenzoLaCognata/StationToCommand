package stationtocommand.view.missionStructure;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class MissionVehicleListView {

    private final UtilsView utilsView;
    private final MissionVehicleView missionVehicleView;

    public MissionVehicleListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionVehicleView = new MissionVehicleView();
    }

    public MissionVehicleView getMissionVehicleView() {
        return missionVehicleView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionStationLink missionStationLink) {
        utilsView.addLabel(pane, "Vehicles");
/*        for (MissionVehicleLink missionVehicleLink : missionStationLink()) {
            Button button = new Button(missionVehicleLink.getVehicle().toString());
            button.setOnAction(_ -> missionVehicleView.show());
            pane.getChildren().add(button);
        }*/
    }

}
