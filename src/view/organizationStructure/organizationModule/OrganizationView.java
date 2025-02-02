package view.organizationStructure.organizationModule;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.departmentStructure.departmentModule.Department;
import model.stationStructure.stationModule.Station;
import view.departmentStructure.departmentListModule.DepartmentListView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class OrganizationView {

    private final DepartmentListView departmentListView;

    public OrganizationView(UtilsView utilsView) {
        this.departmentListView = new DepartmentListView(utilsView);
    }

    public void show(Pane pane1, Pane pane2, List<Node> nodes1, List<Node> nodes2, List<Department> departments) {
        departmentListView.show(pane1, pane2, nodes1, nodes2, departments);
    }

}
