package stationtocommand.view.mainStructure;

import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.View;
import stationtocommand.view.departmentStructure.DepartmentListView;

import java.util.List;

public class OrganizationView {

    private final DepartmentListView departmentListView;

    public OrganizationView(UtilsView utilsView) {
        this.departmentListView = new DepartmentListView(utilsView);
    }

    // TODO: replace worldMap with View from here downwards
    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, List<Department> departments) {
        departmentListView.show(breadCrumbBar, view, departments);
    }

    public DepartmentListView getDepartmentListView() {
        return departmentListView;
    }

}
