package stationtocommand.view.missionStructure;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
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
        utilsView.addSectionTitleLabel(pane, "Vehicles");
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
                showSidebar(breadCrumbBar, pane, missionVehicleLink);
            }
        }
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionUnitLink missionUnitLink) {
        utilsView.addSectionTitleLabel(pane, "Vehicles");
        for (MissionVehicleLink missionVehicleLink : missionUnitLink.getVehicleLinks()) {
            showSidebar(breadCrumbBar, pane, missionVehicleLink);
        }
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionVehicleLink missionVehicleLink) {
        HBox hBox = new HBox(10);
        pane.getChildren().add(hBox);

        showMissionVehicleIcon(missionVehicleLink, hBox);
        showMissionVehicleButton(breadCrumbBar, pane, missionVehicleLink, hBox);
    }

    private void showMissionVehicleIcon(MissionVehicleLink missionVehicleLink, HBox hBox) {
        ImageView imageView = utilsView.smallIcon(utilsView.vehicleIconPath(missionVehicleLink.getVehicle().getVehicleType()));
        hBox.getChildren().add(imageView);
    }

    private void showMissionVehicleButton(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionVehicleLink missionVehicleLink, HBox hBox) {
        Button button = new Button(missionVehicleLink.getVehicle().toString());
        button.setOnAction(_ -> missionVehicleView.show(breadCrumbBar, pane, missionVehicleLink));
        hBox.getChildren().add(button);

    }

}
