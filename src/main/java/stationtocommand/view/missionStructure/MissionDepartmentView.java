package stationtocommand.view.missionStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationLink;
import stationtocommand.view.mainStructure.UtilsView;

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
