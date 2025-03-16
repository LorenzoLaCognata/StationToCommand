package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionUnitView {

    private final UtilsView utilsView;
    private final MissionResponderListView missionResponderListView;
    private final MissionVehicleListView missionVehicleListView;

    public MissionUnitView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionResponderListView = new MissionResponderListView(utilsView);
        this.missionVehicleListView = new MissionVehicleListView(utilsView);
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionUnitLink missionUnitLink) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, worldMap, missionUnitLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionUnitLink);
        utilsView.clearPane(navigationPanel);
        utilsView.clearPane(worldMap);
        showMissionUnitDetails(navigationPanel, missionUnitLink);
        missionResponderListView.show(breadCrumbBar, navigationPanel, worldMap, missionUnitLink);
        missionVehicleListView.show(breadCrumbBar, navigationPanel, missionUnitLink);
    }

    private void showMissionUnitDetails(Pane navigationPanel, MissionUnitLink missionUnitLink) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.EMPTY, missionUnitLink.getMission().getMissionType().getResourcePath(), missionUnitLink.getMission().getMissionType().toString());
        utilsView.addMainTitleLabel(labelPane, missionUnitLink.getMission().toString());
    }

}
