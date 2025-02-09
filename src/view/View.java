package view;

import controller.Controller;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import model.departmentStructure.Department;
import model.missionStructure.Mission;
import view.mainStructure.DispatchView;
import view.mainStructure.OrganizationView;
import view.mainStructure.UtilsView;

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
    private final Pane centerPane = new Pane();

    public View() {
        ColumnConstraints leftCol = new ColumnConstraints();
        leftCol.setPrefWidth(350);

        ColumnConstraints centerCol = new ColumnConstraints();
        centerCol.setHgrow(Priority.ALWAYS);

        gridPane.getColumnConstraints().addAll(leftCol, centerCol);

        RowConstraints topRow = new RowConstraints();
        topRow.setPrefHeight(60);

        RowConstraints centerRow = new RowConstraints();
        centerRow.setVgrow(Priority.ALWAYS);

        gridPane.getRowConstraints().addAll(topRow, centerRow);

        gridPane.add(topPane, 0, 0, 2, 1); // Top spans both columns
        gridPane.add(leftPane, 0, 1);
        gridPane.add(centerPane, 1, 1);

        GridPane.setHgrow(centerPane, Priority.ALWAYS);
        GridPane.setVgrow(centerPane, Priority.ALWAYS);

        leftPane.setSpacing(15);
        leftPane.setPadding(new Insets(15));
        leftPane.setStyle("-fx-background-color: rgba(20, 20, 20, 0.9); -fx-border-radius: 10;");

        centerPane.setStyle("-fx-background-color: #ffffff;");
        centerPane.widthProperty().addListener((_, _, newValue) -> {
            SCENE_WIDTH = newValue.floatValue();
        });
        centerPane.heightProperty().addListener((_, _, newValue) -> {
            SCENE_HEIGHT = newValue.floatValue();
        });
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
        mapView.fitWidthProperty().bind(centerPane.widthProperty());
        mapView.fitHeightProperty().bind(centerPane.heightProperty());
        mapView.setPreserveRatio(true);
        mapNodes.add(mapView);

        centerPane.getChildren().addAll(mapNodes);

        Button organizationButton = new Button("Organization");
        organizationButton.setOnAction(_ -> {
            List<Node> sidebarNodes = utilsView.clearPane(leftPane);
            List<Node> nextMapNodes = utilsView.resetPane(centerPane, mapNodes);
            organizationView.show(leftPane, centerPane, sidebarNodes, nextMapNodes, departments);
        });

        topPane.getItems().addAll(organizationButton);

        Button dispatchButton = new Button("Dispatch");
        dispatchButton.setOnAction(_ -> {
            List<Node> sidebarNodes = utilsView.clearPane(leftPane);
            List<Node> nextMapNodes = utilsView.resetPane(centerPane, mapNodes);
            dispatchView.show(leftPane, centerPane, sidebarNodes, nextMapNodes, missions);
        });

        topPane.getItems().addAll(dispatchButton);

        Button exitButton = new Button("Quit");
        exitButton.setOnAction(_ -> Platform.exit());
        topPane.getItems().addAll(exitButton);
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
