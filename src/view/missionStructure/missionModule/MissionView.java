package view.missionStructure.missionModule;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.departmentStructure.departmentModule.Department;
import model.linkStructure.organizationLinkModule.DepartmentLink;
import model.missionStructure.missionModule.Mission;
import view.missionStructure.missionListModule.MissionDepartmentListView;
import view.utilsStructure.utilsModule.UtilsView;

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
