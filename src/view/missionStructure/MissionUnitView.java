package view.missionStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.missionStructure.Mission;
import model.responderStructure.Responder;
import model.responderStructure.ResponderLink;
import model.unitStructure.Unit;
import model.vehicleStructure.Vehicle;
import model.vehicleStructure.VehicleLink;
import view.mainStructure.UtilsView;

import java.util.List;

public class MissionUnitView {

    private final MissionResponderListView missionResponderListView;
    private final MissionVehicleListView missionVehicleListView;

    public MissionUnitView(UtilsView utilsView, Mission mission) {
        this.missionResponderListView = new MissionResponderListView(utilsView, mission);
        this.missionVehicleListView = new MissionVehicleListView(utilsView, mission);
    }

    public void show(Pane pane, List<Node> nodes, Mission mission, Unit unit) {
        List<Responder> missionResponders = mission.getDepartmentLinks().stream()
                .flatMap(item -> item.getStationLinks().stream())
                .flatMap(item -> item.getUnitLinks().stream())
                .filter(item -> item.getUnit().equals(unit))
                .flatMap(item -> item.getResponderLinks().stream())
                .map(ResponderLink::getResponder)
                .toList();
        missionResponderListView.show(pane, nodes, mission, missionResponders);
        List<Vehicle> missionVehicles = mission.getDepartmentLinks().stream()
                .flatMap(item -> item.getStationLinks().stream())
                .flatMap(item -> item.getUnitLinks().stream())
                .filter(item -> item.getUnit().equals(unit))
                .flatMap(item -> item.getVehicleLinks().stream())
                .map(VehicleLink::getVehicle)
                .toList();
        missionVehicleListView.show(pane, nodes, mission, missionVehicles);
    }

}
