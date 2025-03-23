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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, MissionStationLink missionStationLink) {
        View.viewRunnable = () -> show(breadCrumbBar, view, missionStationLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionStationLink);
        view.getNavigationPanel().clear();
        view.getWorldMap().clear();
        showSidebar(breadCrumbBar, view, missionStationLink);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, View view, MissionStationLink missionStationLink) {
        showMissionStationDetails(view, missionStationLink);
        missionUnitListView.show(breadCrumbBar, view, missionStationLink);
        missionResponderListView.show(breadCrumbBar, view, missionStationLink);
        missionVehicleListView.show(breadCrumbBar, view, missionStationLink);
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
