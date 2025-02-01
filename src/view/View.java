package view;

import controller.Controller;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.departmentStructure.departmentModule.Department;
import view.departmentStructure.departmentListModule.DepartmentListView;
import view.departmentStructure.departmentModule.DepartmentView;
import view.equipmentStructure.equipmentListView.EquipmentListView;
import view.equipmentStructure.equipmentModule.EquipmentView;
import view.organizationStructure.organizationModule.OrganizationView;
import view.responderStructure.responderListModule.ResponderListView;
import view.responderStructure.responderModule.ResponderView;
import view.skillStructure.skillListModule.SkillListView;
import view.skillStructure.skillModule.SkillView;
import view.stationStructure.stationListModule.StationListView;
import view.stationStructure.stationModule.StationView;
import view.trainingStructure.trainingListModule.TrainingListView;
import view.trainingStructure.trainingModule.TrainingView;
import view.unitStructure.unitListModule.UnitListView;
import view.unitStructure.unitModule.UnitView;
import view.utilsStructure.utilsModule.UtilsView;
import view.vehicleStructure.vehicleListModule.VehicleListView;
import view.vehicleStructure.vehicleModule.VehicleView;

import java.util.List;

public class View {

    private Controller controller;
    private UtilsView utilsView;
    private OrganizationView organizationView;
    private final BorderPane parentScene = new BorderPane();
    private final ToolBar topBar = new ToolBar();
    private final VBox leftPanel = new VBox(10);
    private final Pane centerArea = new Pane();

    public View() {
        parentScene.setTop(topBar);
        parentScene.setLeft(leftPanel);
        parentScene.setCenter(centerArea);
        topBar.setMinHeight(60);

        leftPanel.setMinWidth(400);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setStyle("-fx-background-color: #f0f0f0;");

        centerArea.setStyle("-fx-background-color: #ffffff;");

        ImageView mapView = new ImageView(new Image("file:C:\\Users\\vodev\\OneDrive\\Desktop\\map.jpg"));
        mapView.fitWidthProperty().bind(centerArea.widthProperty());
        mapView.fitHeightProperty().bind(centerArea.heightProperty());
        mapView.setPreserveRatio(true);
        centerArea.getChildren().add(mapView);

        Circle station = new Circle(100, 100, 12, Color.BLUE);
        Circle responder = new Circle(200, 200, 8, Color.RED);
        centerArea.getChildren().addAll(station, responder);

    }

    public void initialize(Controller controller) {
        this.controller = controller;
        this.utilsView = new UtilsView();
        this.organizationView = new OrganizationView(utilsView);
    }

    public void generateTopBar(List<Department> departments) {
        Button organizationButton = new Button("Organization");
        organizationButton.setOnAction(_ -> {
            List<Control> controls = utilsView.setBreadcrumbs(leftPanel);
            organizationView.show(leftPanel, controls, departments);
        });

        topBar.getItems().addAll(organizationButton);

        Button exitButton = new Button("Quit");
        exitButton.setOnAction(_ -> Platform.exit());
        topBar.getItems().addAll(exitButton);
    }

    public BorderPane getParentScene() {
        return parentScene;
    }

    public ToolBar getTopBar() {
        return topBar;
    }

    public VBox getLeftPanel() {
        return leftPanel;
    }

    public Pane getCenterArea() {
        return centerArea;
    }

}
