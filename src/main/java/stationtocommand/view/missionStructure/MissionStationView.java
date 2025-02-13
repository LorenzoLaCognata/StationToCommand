package stationtocommand.view.missionStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.missionLinkStructure.MissionStationLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitLink;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleLink;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;
import java.util.Objects;

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
