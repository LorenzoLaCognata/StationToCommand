package stationtocommand.view.organizationStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class OrganizationView {

    private final UtilsView utilsView;
    private final SortedMap<Department, DepartmentView> departmentViews;

    public OrganizationView(List<Department> departments, View view, UtilsView utilsView) {
        this.departmentViews = departments.stream()
                                .collect(Collectors.toMap(
                                        department -> department,
                                        department -> new DepartmentView(department, view, utilsView),
                                        (_, b) -> b,
                                        TreeMap::new
                                ));

        this.utilsView = utilsView;
    }

    public DepartmentView getDepartmentView(Department department) {
        return departmentViews.get(department);
    }

    public void showOrganization(View view) {
        addOrganizationTitle(view);
        view.getWorldMap().setMapElementsNotVisible();
        for (DepartmentView departmentView : departmentViews.values()) {
            departmentView.addOrganizationDetailsDepartment(view);
            utilsView.setGroupVisible(view.getWorldMap().getMapElementsLayer(), departmentView.getStationIcons());
        }
    }

    private void addOrganizationTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        for (DepartmentView departmentView : departmentViews.values()) {
            utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, departmentView.getDepartment().getDepartmentType());
        }
        utilsView.addMainTitleLabel(horizontalTitlePane, "Organization");
    }

}
