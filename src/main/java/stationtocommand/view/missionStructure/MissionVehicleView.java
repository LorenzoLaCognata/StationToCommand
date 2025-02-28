package stationtocommand.view.missionStructure;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionVehicleView {

    private final UtilsView utilsView;

    public MissionVehicleView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionVehicleLink missionVehicleLink) {
        View.viewRunnable = () -> show(breadCrumbBar, pane, missionVehicleLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionVehicleLink);
        utilsView.clearPane(pane);
        showSidebar(pane, missionVehicleLink);
    }

    private void showSidebar(Pane pane, MissionVehicleLink missionVehicleLink) {
        showMissionVehicleDetails(pane, missionVehicleLink);
    }

    private void showMissionVehicleDetails(Pane pane, MissionVehicleLink missionVehicleLink) {
        HBox hBox = new HBox(10);
        ImageView imageView = utilsView.mediumIcon(utilsView.missionIconPath(missionVehicleLink.getMission().getMissionType()));
        utilsView.addNodeToPane(hBox, imageView);
        utilsView.addMainTitleLabel(hBox, missionVehicleLink.getMission().toString());
        utilsView.addNodeToPane(pane, hBox);


    }

}
