package view.missionStructure.missionModule;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import model.linkStructure.organizationLinkModule.UnitLink;
import model.linkStructure.personLinkModule.ResponderLink;
import model.linkStructure.vehicleLinkModule.VehicleLink;
import model.missionStructure.missionModule.Mission;
import model.responderStructure.responderModule.Responder;
import model.stationStructure.stationModule.Station;
import model.unitStructure.unitModule.Unit;
import model.vehicleStructure.vehicleModule.Vehicle;
import view.missionStructure.missionListModule.MissionResponderListView;
import view.missionStructure.missionListModule.MissionUnitListView;
import view.missionStructure.missionListModule.MissionVehicleListView;
import view.utilsStructure.utilsModule.UtilsView;

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

    public void show(Pane pane, List<Control> controls, Mission mission, Station station) {
        List<Unit> missionUnits = mission.getDepartmentLinks().stream()
                .flatMap(item -> item.getStationLinks().stream())
                .flatMap(item -> item.getUnitLinks().stream())
                .map(UnitLink::getUnit)
                .toList();;
        missionUnitListView.show(pane, controls, mission, missionUnits);
        List<Responder> missionResponders = mission.getDepartmentLinks().stream()
                .flatMap(item -> item.getStationLinks().stream())
                .flatMap(item -> item.getUnitLinks().stream())
                .flatMap(item -> item.getResponderLinks().stream())
                .map(ResponderLink::getResponder)
                .toList();;
        missionResponderListView.show(pane, controls, mission, missionResponders);
        List<Vehicle> missionVehicles = mission.getDepartmentLinks().stream()
                .flatMap(item -> item.getStationLinks().stream())
                .flatMap(item -> item.getUnitLinks().stream())
                .flatMap(item -> item.getVehicleLinks().stream())
                .map(VehicleLink::getVehicle)
                .toList();;
        missionVehicleListView.show(pane, controls, mission, missionVehicles);
    }

}
