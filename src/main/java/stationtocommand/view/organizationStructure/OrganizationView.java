package stationtocommand.view.organizationStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class OrganizationView {

    private final UtilsView utilsView;
    private final SortedMap<Department, DepartmentView> departmentViews;

    public OrganizationView(List<Department> departments, View view, UtilsView utilsView) {
        this.departmentViews = new TreeMap<>();
        for (Department department : departments) {
            DepartmentView departmentView = new DepartmentView(department, view, utilsView);
            departmentViews.put(department, departmentView);
        }
        this.utilsView = utilsView;
    }

    public DepartmentView getDepartmentView(Department department) {
        return departmentViews.get(department);
    }

    public void show(View view) {
        showNavigationPanel(view);
        showMap(view);
    }

    private void showNavigationPanel(View view) {
        utilsView.addTitle(view.getTitlePane(), "Organization");
        for (DepartmentView departmentView : departmentViews.values()) {
            departmentView.addListDetails(view);
        }
    }

    private void showMap(View view) {
        view.hideMap();
        for (DepartmentView departmentView : departmentViews.values()) {
            for (StationView stationView : departmentView.getStationViews().values()) {
                stationView.showNode();
            }
        }
    }

}