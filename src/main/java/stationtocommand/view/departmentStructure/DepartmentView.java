package stationtocommand.view.departmentStructure;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.stationStructure.StationListView;

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
