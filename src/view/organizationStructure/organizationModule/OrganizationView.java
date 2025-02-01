package view.organizationStructure.organizationModule;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import model.departmentStructure.departmentModule.Department;
import view.departmentStructure.departmentListModule.DepartmentListView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class OrganizationView {

    private final DepartmentListView departmentListView;

    public OrganizationView(UtilsView utilsView) {
        this.departmentListView = new DepartmentListView(utilsView);
    }

    public void show(Pane pane, List<Control> controls, List<Department> departments) {
        departmentListView.show(pane, controls, departments);
    }

}
