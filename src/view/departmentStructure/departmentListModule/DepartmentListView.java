package view.departmentStructure.departmentListModule;

import javafx.scene.Node;
import javafx.scene.control.*;
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

    public void show(Pane pane1, Pane pane2, List<Node> nodes1, List<Node> nodes2, List<Department> departments) {


        Label header = new Label("Departments");
        //pane1.getChildren().addAll(header);

        TreeItem<Node> headerTreeItem = new TreeItem<>(header);

        TreeView<Node> treeView = new TreeView<>();
        treeView.setRoot(headerTreeItem);
        pane1.getChildren().add(treeView);

        for (Department department : departments) {
            String text1 = department.getDepartmentType().toString() + " Department";
            String text2 = department.getStations().size() + " stations";
            Button button = new Button();
            button.setOnAction(_ -> {
                List<Node> nextNodes1 = utilsView.resetAndAddToPane(pane1, nodes1, button);
                List<Node> nextNodes2 = utilsView.resetPane(pane2, nodes2);
                departmentView.show(pane1, pane2, nextNodes1, nextNodes2, department);
            });
            Tooltip tooltip = new Tooltip(text2);
            button.setTooltip(tooltip);

            TreeItem<Node> departmentTreeItem = new TreeItem<>(button);
            headerTreeItem.getChildren().add(departmentTreeItem);
            utilsView.addToSidebar(pane1, button, text1, text2);
        }
    }

}
