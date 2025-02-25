package stationtocommand.view.missionStructure;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionVehicleListView {

    private final UtilsView utilsView;
    private final MissionVehicleView missionVehicleView;

    public MissionVehicleListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionVehicleView = new MissionVehicleView(utilsView);
    }

    public MissionVehicleView getMissionVehicleView() {
        return missionVehicleView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionStationLink missionStationLink) {
        utilsView.addHeadingLabelToPane(pane, "Vehicles");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
                Button button = new Button(missionVehicleLink.getVehicle().toString());
                button.setOnAction(_ -> missionVehicleView.show(breadCrumbBar, pane, missionVehicleLink));
                pane.getChildren().add(button);
            }
        }
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionUnitLink missionUnitLink) {
        utilsView.addHeadingLabelToPane(pane, "Vehicles");
        for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
            Button button = new Button(missionVehicleLink.getVehicle().toString());
            button.setOnAction(_ -> missionVehicleView.show(breadCrumbBar, pane, missionVehicleLink));
            pane.getChildren().add(button);
        }
    }

}
