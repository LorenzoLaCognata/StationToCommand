package stationtocommand.view.organizationStructure;

import javafx.application.Platform;
import javafx.scene.chart.PieChart;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.*;

public class OrganizationView {

    private final UtilsView utilsView;
    private final Map<Department, DepartmentView> departmentViews;


    // Constructor

    public OrganizationView(List<Department> departments, View view, UtilsView utilsView) {
        this.departmentViews = new LinkedHashMap<>();
        for (Department department : departments) {
            addDepartmentView(department, view, utilsView);
        }
        this.utilsView = utilsView;
    }

    private void addDepartmentView(Department department, View view, UtilsView utilsView) {
        if (!departmentViews.containsKey(department)) {
            DepartmentView departmentView = new DepartmentView(department, view, utilsView);
            departmentViews.put(department, departmentView);
        }
    }


    // Getter

    public DepartmentView getDepartmentView(Department department) {
        return departmentViews.get(department);
    }


    // Methods

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