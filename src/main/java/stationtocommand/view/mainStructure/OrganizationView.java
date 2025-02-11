package stationtocommand.view.mainStructure;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.departmentStructure.DepartmentListView;

import java.util.List;

public class OrganizationView {

    private final UtilsView utilsView;
    private final DepartmentListView departmentListView;

    public OrganizationView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.departmentListView = new DepartmentListView(utilsView);
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, List<Node> nodes1, List<Node> nodes2, List<Department> departments) {
        utilsView.addTreeItem(pane1, "Departments", TreeItemType.DEPARTMENT_HEADER, null, null, null);
        departmentListView.show(breadCrumbBar, pane1, pane2, nodes1, nodes2, departments);
    }

    public DepartmentListView getDepartmentListView() {
        return departmentListView;
    }

}
