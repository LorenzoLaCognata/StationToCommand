package view.mainStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import model.departmentStructure.Department;
import view.departmentStructure.DepartmentListView;

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
