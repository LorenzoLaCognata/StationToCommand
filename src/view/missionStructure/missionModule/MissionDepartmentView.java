package view.missionStructure.missionModule;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.departmentStructure.departmentModule.Department;
import model.linkStructure.organizationLinkModule.StationLink;
import model.missionStructure.missionModule.Mission;
import model.stationStructure.stationModule.Station;
import view.missionStructure.missionListModule.MissionStationListView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class MissionDepartmentView {

    private final Mission mission;
    private final MissionStationListView missionStationListView;

    public MissionDepartmentView(UtilsView utilsView, Mission mission) {
        this.mission = mission;
        this.missionStationListView = new MissionStationListView(utilsView, mission);
    }

    public void show(Pane pane, List<Node> nodes, Mission mission, Department department) {
        List<Station> stations = mission.getDepartmentLinks().stream()
                                    .flatMap(item -> item.getStationLinks().stream())
                                    .map(StationLink::getStation)
                                    .toList();
        missionStationListView.show(pane, nodes, mission, stations);
    }

}
