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
    private DepartmentView departmentView;
    private DepartmentListView departmentListView;
    private StationView stationView;
    private StationListView stationListView;
    private UnitView unitView;
    private UnitListView unitListView;
    private ResponderView responderView;
    private ResponderListView responderListView;
    private SkillView skillView;
    private SkillListView skillListView;
    private TrainingView trainingView;
    private TrainingListView trainingListView;
    private VehicleView vehicleView;
    private VehicleListView vehicleListView;
    private EquipmentView equipmentView;
    private EquipmentListView equipmentListView;

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
        this.vehicleView = new VehicleView();
        this.vehicleListView = new VehicleListView(utilsView, vehicleView);
        this.equipmentView = new EquipmentView();
        this.equipmentListView = new EquipmentListView(utilsView, equipmentView);
        this.skillView = new SkillView();
        this.skillListView = new SkillListView(utilsView, skillView);
        this.trainingView = new TrainingView();
        this.trainingListView = new TrainingListView(utilsView, trainingView);
        this.responderView = new ResponderView(skillListView, trainingListView);
        this.responderListView = new ResponderListView(utilsView, responderView);
        this.unitView = new UnitView(responderListView, vehicleListView, equipmentListView);
        this.unitListView = new UnitListView(utilsView, unitView);
        this.stationView = new StationView(unitListView, responderListView, vehicleListView);
        this.stationListView = new StationListView(utilsView, stationView);
        this.departmentView = new DepartmentView(stationListView);
        this.departmentListView = new DepartmentListView(utilsView, departmentView);
        this.organizationView = new OrganizationView(departmentListView);
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

    public UtilsView getUtilsView() {
        return utilsView;
    }

    public OrganizationView getOrganizationView() {
        return organizationView;
    }

    public DepartmentView getDepartmentView() {
        return departmentView;
    }

    public StationView getStationView() {
        return stationView;
    }

    public SkillView getSkillView() {
        return skillView;
    }

    public TrainingView getTrainingView() {
        return trainingView;
    }

    public VehicleView getVehicleView() {
        return vehicleView;
    }

    public EquipmentView getEquipmentView() {
        return equipmentView;
    }

}
