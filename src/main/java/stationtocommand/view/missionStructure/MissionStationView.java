package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
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

    public void show(View view, MissionStationLink missionStationLink) {
        View.viewRunnable = () -> show(view, missionStationLink);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), missionStationLink);
        view.getNavigationPanel().clear();
        view.getWorldMap().clear();
        showSidebar(view, missionStationLink);
    }

    private void showSidebar(View view, MissionStationLink missionStationLink) {
        showMissionStationDetails(view, missionStationLink);
        missionUnitListView.show(view, missionStationLink);
        missionResponderListView.show(view, missionStationLink);
        missionVehicleListView.show(view, missionStationLink);
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
                missionResponderListView.showMap(view, missionResponderLink);
            }
        }
    }

    private void showMissionStationDetails(View view, MissionStationLink missionStationLink) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, missionStationLink.getMission().getMissionType().getResourcePath(), missionStationLink.getMission().getMissionType().toString());
        utilsView.addMainTitleLabel(horizontalTitlePane, missionStationLink.getMission().toString());
    }

}
