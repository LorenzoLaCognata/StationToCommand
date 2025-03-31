package stationtocommand.view.mainStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.View;
import stationtocommand.view.departmentStructure.DepartmentListView;

import java.util.List;

public class OrganizationView {

    private final UtilsView utilsView;
    private final DepartmentListView departmentListView;

    public OrganizationView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.departmentListView = new DepartmentListView(utilsView);
    }

    public void show(View view, List<Department> departments) {
        showOrganizationDetails(view, departments);
        departmentListView.show(view, departments);
    }

    public DepartmentListView getDepartmentListView() {
        return departmentListView;
    }

    private void showOrganizationDetails(View view, List<Department> departments) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        for (Department department : departments) {
            utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, department.getDepartmentType().getResourcePath(), "");
        }
        utilsView.addMainTitleLabel(horizontalTitlePane, "Organization");
    }

}
