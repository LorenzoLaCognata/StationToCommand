package view.missionStructure.missionModule;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import model.departmentStructure.departmentModule.Department;
import model.linkStructure.organizationLinkModule.DepartmentLink;
import model.linkStructure.personLinkModule.ResponderLink;
import model.missionStructure.missionModule.Mission;
import view.departmentStructure.departmentListModule.DepartmentListView;
import view.stationStructure.stationListModule.StationListView;
import view.unitStructure.unitListModule.UnitListView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;
import java.util.stream.Collectors;

public class MissionView {

    private final DepartmentListView departmentListView;

    public MissionView(UtilsView utilsView) {
        this.departmentListView = new DepartmentListView(utilsView);
    }

    public void show(Pane pane, List<Control> controls, Mission mission) {
        List<Department> departments = mission.getDepartmentLinks().stream()
                                            .map(DepartmentLink::getDepartment)
                                            .toList();
        departmentListView.show(pane, controls, departments);
    }

}
