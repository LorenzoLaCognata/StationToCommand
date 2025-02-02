package view.departmentStructure.departmentListModule;

import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import model.departmentStructure.departmentModule.Department;
import view.departmentStructure.departmentModule.DepartmentView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class DepartmentListView {

    private final UtilsView utilsView;
    private final DepartmentView departmentView;

    public DepartmentListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.departmentView = new DepartmentView(utilsView);
    }

    public void show(Pane sidebar, Pane map, List<Control> sidebarNodes, List<Control> mapNodes, List<Department> departments) {
        System.out.println("4: "+ map.getChildren().size());
        Label stationsSeparator = new Label("----------------------\nDepartments");
        sidebar.getChildren().addAll(stationsSeparator);

        for (Department department : departments) {
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Control> nextSidebar = utilsView.setPane(sidebar, sidebarNodes, button);
                System.out.println("4a: "+ map.getChildren().size());
                List<Control> nextMap = utilsView.setPane(map, mapNodes);
                System.out.println("4b: "+ map.getChildren().size());
                departmentView.show(sidebar, map, nextSidebar, nextMap, department);
            });
            String text1 = department.getDepartmentType().toString() + " Department";
            String text2 = department.getStations().size() + " stations";
            utilsView.addToSidebar(sidebar, button, text1, text2);
        }
    }

}
