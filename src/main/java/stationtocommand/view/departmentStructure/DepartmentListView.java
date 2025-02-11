package stationtocommand.view.departmentStructure;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.mainStructure.TreeItemType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.ArrayList;
import java.util.List;


public class DepartmentListView {

    private final UtilsView utilsView;
    private final DepartmentView departmentView;

    public DepartmentListView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.departmentView = new DepartmentView(utilsView);
    }

    public DepartmentView getDepartmentView() {
        return departmentView;
    }

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane pane1, Pane pane2, List<Node> nodes1, List<Node> nodes2, List<Department> departments) {

        for (Department department : departments) {
            String text1 = department.getDepartmentType().toString() + " Department";
            String text2 = department.getStations().size() + " stations";
            EventHandler<MouseEvent> eventHandler = _ -> {
                // TODO: restore or delete
                //List<Node> nextNodes1 = utilsView.resetAndAddToPane(pane1, nodes1, button);
                List<Node> nextNodes1 = new ArrayList<>();
                List<Node> nextNodes2 = utilsView.resetPane(pane2, nodes2);
                departmentView.show(breadCrumbBar, pane1, pane2, nextNodes1, nextNodes2, department);

            };

            utilsView.addTreeItem(pane1, text1, TreeItemType.DEPARTMENT_ITEM, department, TreeItemType.DEPARTMENT_HEADER, null);

        }

    }

}
