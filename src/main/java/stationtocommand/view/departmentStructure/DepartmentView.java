package stationtocommand.view.departmentStructure;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.mainStructure.TreeItemType;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.stationStructure.StationListView;

import java.util.List;

public class DepartmentView {

    private final UtilsView utilsView;
    private final StationListView stationListView;

    public DepartmentView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.stationListView = new StationListView(utilsView);
    }

    public StationListView getStationListView() {
        return stationListView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, List<Node> nodes1, List<Node> nodes2, Department department) {
        utilsView.addBreadCrumb(breadCrumbBar, department);
        utilsView.clearTreeItemChildren(pane1, TreeItemType.DEPARTMENT_ITEM, department);
        utilsView.addTreeItem(pane1, "Stations", TreeItemType.STATION_HEADER, null, TreeItemType.DEPARTMENT_ITEM, department);
        stationListView.show(breadCrumbBar, pane1, pane2, nodes1, nodes2, department.getStations());
    }

}
