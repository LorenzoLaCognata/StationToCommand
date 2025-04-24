package stationtocommand.view.organizationStructure;

import javafx.scene.layout.Pane;
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

    public void showOrganization(View view) {
        showOrganizationDetails(view);
        showOrganizationMap(view);
    }

    private void showOrganizationDetails(View view) {
        addOrganizationTitle(view);
        for (DepartmentView departmentView : departmentViews.values()) {
            departmentView.addOrganizationDetailsDepartment(view);
        }
    }

    private void addOrganizationTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addMainTitleLabel(horizontalTitlePane, "Organization");
    }

    private void showOrganizationMap(View view) {
        view.getWorldMap().setMapElementsNotVisible();
        for (DepartmentView departmentView : departmentViews.values()) {
            for (StationView stationView : departmentView.getStationViews().values()) {
                stationView.setNodeVisible();
            }
        }
    }

}
