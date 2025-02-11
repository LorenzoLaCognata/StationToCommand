package stationtocommand.view;

import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import stationtocommand.controller.Controller;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.view.mainStructure.*;
import org.controlsfx.control.BreadCrumbBar;

import java.util.ArrayList;
import java.util.List;

public class View {

    public static float SCENE_WIDTH;
    public static float SCENE_HEIGHT;

    private Controller controller;
    private UtilsView utilsView;
    private OrganizationView organizationView;
    private DispatchView dispatchView;
    private final GridPane gridPane = new GridPane();
    private final ToolBar topPane = new ToolBar();
    private final VBox leftPane = new VBox(10);
    private final TreeView<String> leftTreeView = new TreeView<>();
    private final Pane centerPane = new Pane();
    BreadCrumbBar<Object> breadCrumbBar = new BreadCrumbBar<>();

    public View() {
        leftPane.setSpacing(15);
        leftPane.setPadding(new Insets(15));
        leftPane.setStyle("-fx-background-color: rgba(20, 20, 20, 0.9); -fx-border-radius: 10;");

        centerPane.setStyle("-fx-background-color: #ffffff;");
        centerPane.widthProperty().addListener((_, _, newValue) -> SCENE_WIDTH = newValue.floatValue());
        centerPane.heightProperty().addListener((_, _, newValue) -> SCENE_HEIGHT = newValue.floatValue());

        gridPane.add(topPane, 0, 0, 2, 1);
        gridPane.add(breadCrumbBar, 0, 1);
        gridPane.add(leftPane, 0, 2);
        gridPane.add(centerPane, 1, 1, 1, 2);

    }

    public void initialize(Controller controller) {
        this.controller = controller;
        this.utilsView = new UtilsView();
        this.organizationView = new OrganizationView(utilsView);
        this.dispatchView = new DispatchView(utilsView);
    }

    public void generateUI(List<Department> departments, List<Mission> missions) {

        ColumnConstraints firstCol = new ColumnConstraints();
        firstCol.setMinWidth(350);
        firstCol.setMaxWidth(350);

        ColumnConstraints secondCol = new ColumnConstraints();
        // TODO: change width to grow dynamically, fixed as it was causing issues
        secondCol.setMinWidth(1150);
        secondCol.setMaxWidth(1150);

        gridPane.getColumnConstraints().addAll(firstCol, secondCol);

        RowConstraints firstRow = new RowConstraints();
        firstRow.setMinHeight(60);
        firstRow.setMaxHeight(60);

        RowConstraints secondRow = new RowConstraints();
        secondRow.setMinHeight(40);
        secondRow.setMaxHeight(40);

        RowConstraints thirdRow = new RowConstraints();
        // TODO: change width to grow dynamically, fixed as it was causing issues
        thirdRow.setMinHeight(750);
        thirdRow.setMaxHeight(750);

        gridPane.getRowConstraints().addAll(firstRow, secondRow, thirdRow);

        leftPane.getChildren().add(leftTreeView);

        List<Node> mapNodes = new ArrayList<>();
        ImageView mapView = new ImageView(new Image("file:C:\\Users\\vodev\\OneDrive\\Desktop\\map.jpg"));
        mapView.fitWidthProperty().bind(centerPane.widthProperty());
        mapView.fitHeightProperty().bind(centerPane.heightProperty());
        mapView.setPreserveRatio(true);
        mapNodes.add(mapView);

        centerPane.getChildren().addAll(mapNodes);

        Button organizationButton = new Button("Organization");
        organizationButton.setOnAction(_ -> {
            // TODO: delete or restore
            //List<Node> sidebarNodes = utilsView.clearPane(leftPane);
            List<Node> sidebarNodes = utilsView.resetPane(leftPane, List.of(leftTreeView));
            List<Node> nextMapNodes = utilsView.resetPane(centerPane, mapNodes);
            utilsView.resetBreadCrumbBar(breadCrumbBar);
            utilsView.clearTreeView(leftPane);
            organizationView.show(breadCrumbBar, leftPane, centerPane, sidebarNodes, nextMapNodes, departments);
        });

        topPane.getItems().addAll(organizationButton);

        Button dispatchButton = new Button("Dispatch");
        dispatchButton.setOnAction(_ -> {
            List<Node> sidebarNodes = utilsView.clearPane(leftPane);
            List<Node> nextMapNodes = utilsView.resetPane(centerPane, mapNodes);
            utilsView.resetBreadCrumbBar(breadCrumbBar);
            utilsView.clearTreeView(leftPane);
            dispatchView.show(leftPane, centerPane, sidebarNodes, nextMapNodes, missions);
        });

        topPane.getItems().addAll(dispatchButton);

        Button exitButton = new Button("Quit");
        exitButton.setOnAction(_ -> Platform.exit());
        topPane.getItems().addAll(exitButton);

        breadCrumbBar.setOnCrumbAction(event -> {
            Object selectedObject = event.getSelectedCrumb().getValue();

            if (selectedObject instanceof Department) {
                List<Node> sidebarNodes = utilsView.clearPane(leftPane);
                List<Node> nextMapNodes = utilsView.resetPane(centerPane, mapNodes);
                utilsView.resetBreadCrumbBar(breadCrumbBar);
                organizationView.getDepartmentListView().getDepartmentView().show(breadCrumbBar, leftPane, centerPane, sidebarNodes, nextMapNodes, (Department) selectedObject);
            } else if (selectedObject instanceof Station) {
                List<Node> sidebarNodes = utilsView.clearPane(leftPane);
                utilsView.resetBreadCrumbBar(breadCrumbBar);
                organizationView.getDepartmentListView().getDepartmentView().getStationListView().getStationView().show(breadCrumbBar, leftPane, sidebarNodes, (Station) selectedObject);
            }
        });

        breadCrumbBar.setStyle("-fx-background-color: #222; -fx-padding: 5px;");

        // TODO: review if this part is useful at all or not
        leftTreeView.setCellFactory(tv -> new TreeCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : item);

                setOnMouseClicked(event -> {
                    if (!isEmpty() && getTreeItem() instanceof CustomTreeItem<?>) {
                        CustomTreeItem<String> customItem = (CustomTreeItem<String>) getTreeItem();

                        switch (customItem.getType()) {
                            case DEPARTMENT_ITEM -> {
                                List<Node> sidebarNodes = new ArrayList<>();
                                List<Node> nextMapNodes = utilsView.resetPane(centerPane, mapNodes);
                                utilsView.resetBreadCrumbBar(breadCrumbBar);
                                utilsView.clearTreeItemChildren(leftPane, TreeItemType.DEPARTMENT_ITEM, customItem.getObject());
                                organizationView.getDepartmentListView().getDepartmentView().show(breadCrumbBar, leftPane, centerPane, sidebarNodes, nextMapNodes, (Department) customItem.getObject());
                            }
                            case STATION_ITEM -> System.out.println("Station clicked: " + customItem.getValue());
                        }
                    }
                });
            }
        });

    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public ToolBar getTopPane() {
        return topPane;
    }

    public VBox getLeftPane() {
        return leftPane;
    }

    public Pane getCenterPane() {
        return centerPane;
    }

}
