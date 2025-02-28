package stationtocommand.view.missionStructure;

import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionResponderLink;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionLinkStructure.MissionUnitLink;
import stationtocommand.view.View;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionStationLink missionStationLink) {
        View.viewRunnable = () -> show(breadCrumbBar, pane1, pane2, missionStationLink);
        utilsView.addBreadCrumb(breadCrumbBar, missionStationLink);
        utilsView.clearPane(pane1);
        utilsView.clearPane(pane2);
        showSidebar(breadCrumbBar, pane1, pane2, missionStationLink);
    }

    private void showSidebar(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, MissionStationLink missionStationLink) {
        showMissionStationDetails(pane1, missionStationLink);
        missionUnitListView.show(breadCrumbBar, pane1, pane2, missionStationLink);
        missionResponderListView.show(breadCrumbBar, pane1, pane2, missionStationLink);
        missionVehicleListView.show(breadCrumbBar, pane1, missionStationLink);
        for (MissionUnitLink missionUnitLink : missionStationLink.getUnitLinks()) {
            for (MissionResponderLink missionResponderLink : missionUnitLink.getResponderLinks()) {
                missionResponderListView.showMap(pane2, missionResponderLink);
            }
        }
    }

    private void showMissionStationDetails(Pane pane, MissionStationLink missionStationLink) {
        HBox hBox = new HBox(10);
        ImageView imageView = utilsView.mediumIcon(utilsView.missionIconPath(missionStationLink.getMission().getMissionType()));
        utilsView.addNodeToPane(hBox, imageView);
        utilsView.addMainTitleLabel(hBox, missionStationLink.getMission().toString());
        utilsView.addNodeToPane(pane, hBox);
    }

}
