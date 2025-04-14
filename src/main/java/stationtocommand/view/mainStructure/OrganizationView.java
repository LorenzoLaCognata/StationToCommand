package stationtocommand.view.mainStructure;

import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.View;
import stationtocommand.view.departmentStructure.DepartmentView;

import java.util.List;

public class OrganizationView {

    private final UtilsView utilsView;
    private final DepartmentView departmentView;

    public OrganizationView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.departmentView = new DepartmentView(utilsView);
    }

    public DepartmentView getDepartmentView() {
        return departmentView;
    }

    public void show(View view, List<Department> departments) {
        organizationTitle(view, departments);
        organizationDetails(view, departments);
    }

    private void organizationTitle(View view, List<Department> departments) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        for (Department department : departments) {
            utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, department.getDepartmentType());
        }
        utilsView.addMainTitleLabel(horizontalTitlePane, "Organization");
    }

    private void organizationDetails(View view, List<Department> departments) {
        // TODO: review Map clearing
        view.getWorldMap().clear();
        for (Department department : departments) {
            departmentView.organizationDetailsDepartmentEntry(view, department);
            departmentView.organizationMapDepartmentEntry(view, department);
        }
    }

}