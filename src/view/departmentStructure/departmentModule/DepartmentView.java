package view.departmentStructure.departmentModule;

import javafx.scene.control.Control;
import javafx.scene.layout.Pane;
import model.departmentStructure.departmentModule.Department;
import view.stationStructure.stationListModule.StationListView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.List;

public class DepartmentView {

    private final StationListView stationListView;

    public DepartmentView(UtilsView utilsView) {
        this.stationListView = new StationListView(utilsView);
    }

    public void show(Pane sidebar, Pane map, List<Control> sidebarNodes, List<Control> mapNodes, Department department) {
        System.out.println("5: "+ map.getChildren().size());
        stationListView.show(sidebar, map, sidebarNodes, mapNodes, department.getStations());
    }

}
