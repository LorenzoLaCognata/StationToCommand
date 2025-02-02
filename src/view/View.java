package view;

import controller.Controller;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.departmentStructure.departmentModule.Department;
import model.missionStructure.missionModule.Mission;
import view.dispatchStructure.dispatchModule.DispatchView;
import view.organizationStructure.organizationModule.OrganizationView;
import view.utilsStructure.utilsModule.UtilsView;

import java.util.ArrayList;
import java.util.List;

public class View {

    private Controller controller;
    private UtilsView utilsView;
    private OrganizationView organizationView;
    private DispatchView dispatchView;
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
    }

    public void initialize(Controller controller) {
        this.controller = controller;
        this.utilsView = new UtilsView();
        this.organizationView = new OrganizationView(utilsView);
        this.dispatchView = new DispatchView(utilsView);
    }

    public void generateUI(List<Department> departments, List<Mission> missions) {

        List<Node> mapNodes = new ArrayList<>();
        ImageView mapView = new ImageView(new Image("file:C:\\Users\\vodev\\OneDrive\\Desktop\\map.jpg"));
        mapView.fitWidthProperty().bind(centerArea.widthProperty());
        mapView.fitHeightProperty().bind(centerArea.heightProperty());
        mapView.setPreserveRatio(true);
        mapNodes.add(mapView);

        Circle responder = new Circle(200, 200, 8, Color.RED);
        mapNodes.add(responder);

        centerArea.getChildren().addAll(mapNodes);

        Button organizationButton = new Button("Organization");
        organizationButton.setOnAction(_ -> {
            List<Node> sidebarNodes = utilsView.clearPane(leftPanel);
            List<Node> nextMapNodes = utilsView.resetPane(centerArea, mapNodes);
            organizationView.show(leftPanel, centerArea, sidebarNodes, nextMapNodes, departments);
        });

        topBar.getItems().addAll(organizationButton);

        Button dispatchButton = new Button("Dispatch");
        dispatchButton.setOnAction(_ -> {
            List<Node> nodes = utilsView.clearPane(leftPanel);
            dispatchView.show(leftPanel, nodes, missions);
        });

        topBar.getItems().addAll(dispatchButton);

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
