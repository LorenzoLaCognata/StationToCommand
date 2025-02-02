package view.departmentStructure.departmentModule;

import javafx.scene.Node;
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

    public void show(Pane pane1, Pane pane2, List<Node> nodes1, List<Node> nodes2, Department department) {
        stationListView.show(pane1, pane2, nodes1, nodes2, department.getStations());
    }

}
