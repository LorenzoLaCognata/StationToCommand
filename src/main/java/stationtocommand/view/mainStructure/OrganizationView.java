package stationtocommand.view.mainStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.departmentStructure.DepartmentListView;

import java.util.List;

public class OrganizationView {

    private final DepartmentListView departmentListView;

    public OrganizationView(UtilsView utilsView) {
        this.departmentListView = new DepartmentListView(utilsView);
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, List<Department> departments) {
        departmentListView.show(breadCrumbBar, pane1, pane2, departments);
    }

    public DepartmentListView getDepartmentListView() {
        return departmentListView;
    }

}
