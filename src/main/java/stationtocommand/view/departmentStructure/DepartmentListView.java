package stationtocommand.view.departmentStructure;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.view.mainStructure.UtilsView;

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

        Label header = new Label("Departments");
        header.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 0;");
        pane1.getChildren().addAll(header);

        for (Department department : departments) {
            String text1 = department.getDepartmentType().toString() + " Department";
            String text2 = department.getStations().size() + " stations";
            // TODO: remove when this part is stable
            Button button = new Button();
            EventHandler<MouseEvent> eventHandler = _ -> {
                // TODO: change so that we don't add the button that does not exist as of now
                List<Node> nextNodes1 = utilsView.resetAndAddToPane(pane1, nodes1, button);
                List<Node> nextNodes2 = utilsView.resetPane(pane2, nodes2);
                departmentView.show(breadCrumbBar, pane1, pane2, nextNodes1, nextNodes2, department);

            };

            utilsView.addToSidebarNew(pane1, text1, text2, eventHandler);

        }
    }

}
