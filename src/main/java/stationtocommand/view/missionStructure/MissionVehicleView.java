package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionVehicleLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionVehicleView {

    private final UtilsView utilsView;

    public MissionVehicleView(UtilsView utilsView) {
        this.utilsView = utilsView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, MissionVehicleLink missionVehicleLink) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, missionVehicleLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionVehicleLink);
        utilsView.clearPane(navigationPanel);
        showSidebar(navigationPanel, missionVehicleLink);
    }

    private void showSidebar(Pane navigationPanel, MissionVehicleLink missionVehicleLink) {
        showMissionVehicleDetails(navigationPanel, missionVehicleLink);
    }

    private void showMissionVehicleDetails(Pane navigationPanel, MissionVehicleLink missionVehicleLink) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.BLANK, utilsView.missionIconPath(missionVehicleLink.getMission().getMissionType()));
        utilsView.addMainTitleLabel(labelPane, missionVehicleLink.getMission().toString());
    }

}