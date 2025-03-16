package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

public class MissionStationView {

    private final UtilsView utilsView;
    private final MissionUnitListView missionUnitListView;
    private final MissionResponderListView missionResponderListView;
    private final MissionVehicleListView missionVehicleListView;

    public MissionStationView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.missionUnitListView = new MissionUnitListView(utilsView);
        this.missionResponderListView = new MissionResponderListView(utilsView);
        this.missionVehicleListView = new MissionVehicleListView(utilsView);
    }

    public MissionUnitListView getMissionUnitListView() {
        return missionUnitListView;
    }

    public MissionResponderListView getMissionResponderListView() {
        return missionResponderListView;
    }

    public MissionVehicleListView getMissionVehicleListView() {
        return missionVehicleListView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionStationLink missionStationLink) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, worldMap, missionStationLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionStationLink);
        utilsView.clearPane(navigationPanel);
        utilsView.clearPane(worldMap);
        showSidebar(breadCrumbBar, navigationPanel, worldMap, missionStationLink);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, MissionStationLink missionStationLink) {
        showMissionStationDetails(navigationPanel, missionStationLink);
        missionUnitListView.show(breadCrumbBar, navigationPanel, worldMap, missionStationLink);
        missionResponderListView.show(breadCrumbBar, navigationPanel, worldMap, missionStationLink);
        missionVehicleListView.show(breadCrumbBar, navigationPanel, missionStationLink);
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
                missionResponderListView.showMap(worldMap   , missionResponderLink);
            }
        }
    }

    private void showMissionStationDetails(Pane navigationPanel, MissionStationLink missionStationLink) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.EMPTY, missionStationLink.getMission().getMissionType().getResourcePath(), missionStationLink.getMission().getMissionType().toString());
        utilsView.addMainTitleLabel(labelPane, missionStationLink.getMission().toString());
    }

}
