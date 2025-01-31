package view.departmentStructure.departmentListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import model.departmentStructure.departmentModule.Department;
import view.departmentStructure.departmentModule.DepartmentView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class DepartmentListView {

    private final UtilsView utilsView;
    private final DepartmentView departmentView;

    public DepartmentListView(UtilsView utilsView, DepartmentView departmentView) {
        this.utilsView = utilsView;
        this.departmentView = departmentView;
    }

    public void show(Pane pane, List<Control> controls, List<Department> departments) {
        for (Department department : departments) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Control> newControls = utilsView.setBreadcrumbs(pane, controls, button);
                departmentView.show(pane, newControls, department);
            });
            String text1 = department.getDepartmentType().toString() + " Department";
            String text2 = department.getStations().size() + " stations";
            utilsView.addPaneEntry(pane, button, text1, text2);
        }
    }

}
