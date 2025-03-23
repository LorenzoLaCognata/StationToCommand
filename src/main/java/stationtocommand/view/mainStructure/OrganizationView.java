package stationtocommand.view.mainStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.View;
import stationtocommand.view.departmentStructure.DepartmentListView;

import java.util.List;

public class OrganizationView {

    private final DepartmentListView departmentListView;

    public OrganizationView(UtilsView utilsView) {
        this.departmentListView = new DepartmentListView(utilsView);
    }

    public void show(View view, List<Department> departments) {
        departmentListView.show(view, departments);
    }

    public DepartmentListView getDepartmentListView() {
        return departmentListView;
    }

}
