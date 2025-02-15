package stationtocommand.view.missionStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane, MissionStationLink missionStationLink) {
        utilsView.addBreadCrumb(breadCrumbBar, missionStationLink);
        utilsView.clearPane(pane);
        missionUnitListView.show(breadCrumbBar, pane, missionStationLink);
        missionResponderListView.show(breadCrumbBar, pane, missionStationLink);
        missionVehicleListView.show(breadCrumbBar, pane, missionStationLink);
    }

}
