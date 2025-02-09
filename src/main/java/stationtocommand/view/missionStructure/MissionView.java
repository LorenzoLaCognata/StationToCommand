package stationtocommand.view.missionStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;

public class MissionView {

    private final MissionDepartmentListView missionDepartmentListView;

    public MissionView(UtilsView utilsView, Mission mission) {
        this.missionDepartmentListView = new MissionDepartmentListView(utilsView, mission);
    }

    public void show(Pane pane, List<Node> nodes, Mission mission) {
        List<Department> departments = mission.getDepartmentLinks().stream()
                                            .map(DepartmentLink::getDepartment)
                                            .toList();
        missionDepartmentListView.show(pane, nodes, mission, departments);
    }

}
