package stationtocommand.view.missionStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
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

public class MissionStationView {

    private final MissionUnitListView missionUnitListView;
    private final MissionResponderListView missionResponderListView;
    private final MissionVehicleListView missionVehicleListView;

    public MissionStationView(UtilsView utilsView, Mission mission) {
        this.missionUnitListView = new MissionUnitListView(utilsView, mission);
        this.missionResponderListView = new MissionResponderListView(utilsView, mission);
        this.missionVehicleListView = new MissionVehicleListView(utilsView, mission);
    }

    public void show(Pane pane, List<Node> nodes, Mission mission, Station station) {
        List<Unit> missionUnits = mission.getDepartmentLinks().stream()
                .flatMap(item -> item.getStationLinks().stream())
                .flatMap(item -> item.getUnitLinks().stream())
                .map(UnitLink::getUnit)
                .toList();;
        missionUnitListView.show(pane, nodes, mission, missionUnits);
        List<Responder> missionResponders = mission.getDepartmentLinks().stream()
                .flatMap(item -> item.getStationLinks().stream())
                .flatMap(item -> item.getUnitLinks().stream())
                .flatMap(item -> item.getResponderLinks().stream())
                .map(ResponderLink::getResponder)
                .toList();;
        missionResponderListView.show(pane, nodes, mission, missionResponders);
        List<Vehicle> missionVehicles = mission.getDepartmentLinks().stream()
                .flatMap(item -> item.getStationLinks().stream())
                .flatMap(item -> item.getUnitLinks().stream())
                .flatMap(item -> item.getVehicleLinks().stream())
                .map(VehicleLink::getVehicle)
                .toList();;
        missionVehicleListView.show(pane, nodes, mission, missionVehicles);
    }

}
