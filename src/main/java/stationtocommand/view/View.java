package stationtocommand.view;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.controller.Controller;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.equipmentStructure.Equipment;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.mainStructure.DispatchView;
import stationtocommand.view.mainStructure.OrganizationView;
import stationtocommand.view.mainStructure.UtilsView;

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
    private final VBox leftPane = new VBox(5);
    private final BreadCrumbBar<Object> breadCrumbBar = new BreadCrumbBar<>();
    private final StackPane centerPane = new StackPane();
    private final Pane mapBackgroundLayer = new Pane();
    private final Pane mapElementsLayer = new Pane();

    public View() {
        leftPane.setSpacing(15);
        leftPane.setPadding(new Insets(15));
        leftPane.setStyle("-fx-background-color: rgba(20, 20, 20, 0.9); -fx-border-radius: 10;");

        centerPane.setStyle("-fx-background-color: #ffffff;");
        centerPane.widthProperty().addListener((_, _, newValue) -> SCENE_WIDTH = newValue.floatValue());
        centerPane.heightProperty().addListener((_, _, newValue) -> SCENE_HEIGHT = newValue.floatValue());
        centerPane.getChildren().addAll(mapBackgroundLayer, mapElementsLayer);

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
        firstCol.setMinWidth(400);
        firstCol.setMaxWidth(400);

        ColumnConstraints secondCol = new ColumnConstraints();
        // TODO: change width to grow dynamically, fixed as it was causing issues
        secondCol.setMinWidth(1100);
        secondCol.setMaxWidth(1100);

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

        ImageView mapView = new ImageView(new Image("file:C:\\Users\\vodev\\OneDrive\\Desktop\\map.jpg"));
        mapView.fitWidthProperty().bind(centerPane.widthProperty());
        mapView.fitHeightProperty().bind(centerPane.heightProperty());
        mapView.setPreserveRatio(true);
        mapBackgroundLayer.getChildren().add(mapView);

        Button organizationButton = new Button("Organization");
        organizationButton.setOnAction(_ -> {
            utilsView.clearPane(leftPane);
            utilsView.clearPane(mapElementsLayer);
            utilsView.resetBreadCrumbBar(breadCrumbBar);
            organizationView.show(breadCrumbBar, leftPane, mapElementsLayer, departments);
        });

        topPane.getItems().addAll(organizationButton);

        Button dispatchButton = new Button("Dispatch");
        dispatchButton.setOnAction(_ -> {
            List<Node> sidebarNodes = utilsView.clearPane(leftPane);
            List<Node> nextMapNodes = utilsView.clearPane(mapElementsLayer);
            utilsView.resetBreadCrumbBar(breadCrumbBar);
            dispatchView.show(leftPane, mapElementsLayer, sidebarNodes, nextMapNodes, missions);
        });

        topPane.getItems().addAll(dispatchButton);

        Button exitButton = new Button("Quit");
        exitButton.setOnAction(_ -> Platform.exit());
        topPane.getItems().addAll(exitButton);

        breadCrumbBar.setOnCrumbAction(event -> {
            Object selectedObject = event.getSelectedCrumb().getValue();

            if (selectedObject instanceof Department) {
                utilsView.clearPane(mapElementsLayer);
                utilsView.resetBreadCrumbBar(breadCrumbBar);
                organizationView.getDepartmentListView().getDepartmentView().show(breadCrumbBar, leftPane, mapElementsLayer, (Department) selectedObject);
            }
            else if (selectedObject instanceof Station) {
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                organizationView.getDepartmentListView().getDepartmentView().getStationListView().getStationView().show(breadCrumbBar, leftPane, centerPane, (Station) selectedObject);
            }
            else if (selectedObject instanceof Unit) {
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                organizationView.getDepartmentListView().getDepartmentView().getStationListView().getStationView().getUnitListView().getUnitView().show(breadCrumbBar, leftPane, (Unit) selectedObject);
            }
            else if (selectedObject instanceof Responder) {
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                organizationView.getDepartmentListView().getDepartmentView().getStationListView().getStationView().getUnitListView().getUnitView().getResponderListView().getResponderView().show(breadCrumbBar, leftPane, (Responder) selectedObject);
            }
            else if (selectedObject instanceof Vehicle) {
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                organizationView.getDepartmentListView().getDepartmentView().getStationListView().getStationView().getUnitListView().getUnitView().getVehicleListView().getVehicleView().show(breadCrumbBar, leftPane, (Vehicle) selectedObject);
            }
            else if (selectedObject instanceof Equipment) {
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                organizationView.getDepartmentListView().getDepartmentView().getStationListView().getStationView().getUnitListView().getUnitView().getEquipmentListView().getEquipmentView().show(breadCrumbBar, leftPane, (Equipment) selectedObject);
            }
        });

        breadCrumbBar.setStyle("-fx-background-color: #222; -fx-padding: 5px;");

    }

    public GridPane getGridPane() {
        return gridPane;
    }

}
