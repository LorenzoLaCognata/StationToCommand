package main;

import departmentStructure.departmentModule.Department;
import gameStructure.gameModule.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import responderStructure.responderModule.Responder;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.Unit;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Game game = new Game();

        ToolBar topBar = new ToolBar();
        VBox leftPanel = new VBox(10);
        Pane centerArea = new Pane();
        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, 800, 600);

        root.setTop(topBar);
        root.setLeft(leftPanel);
        root.setCenter(centerArea);
        primaryStage.setTitle("Station To Command");
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

        Button exitButton = new Button("Quit");
        exitButton.setOnAction(_ -> Platform.exit());

        Button departmentsButton = new Button("Departments");
        departmentsButton.setOnAction(_ -> {
            leftPanel.getChildren().clear();

            for (Department department : game.departmentManager.getDepartments()) {
                Button departmentButton = new Button(department.toString());
                departmentButton.setOnAction(_ -> {
                    leftPanel.getChildren().clear();

                    for (Station station : department.getStations()) {
                        Button stationButton = new Button(station.toString());
                        stationButton.setOnAction(_ -> {
                            leftPanel.getChildren().clear();

                            for (Unit unit : station.getUnits()) {
                                Button unitButton = new Button(unit.toString());
                                unitButton.setOnAction(_ -> {
                                    leftPanel.getChildren().clear();

                                    for (Responder responder : game.responderManager.getResponders(unit)) {
                                        Button responderButton = new Button(responder.toString());
                                        Label responderLabel = new Label(responder.getRank().toString());
                                        leftPanel.getChildren().addAll(responderButton, responderLabel);
                                    }
                                });
                                Label unitLabel = new Label(game.responderManager.getResponders(unit).size() + " responders");
                                leftPanel.getChildren().addAll(unitButton, unitLabel);
                            }
                        });
                        Label stationLabel = new Label(station.getUnits().size() + " units");
                        leftPanel.getChildren().addAll(stationButton, stationLabel);
                    }
                });
                Label label = new Label(department.getStations().size() + " stations");
                leftPanel.getChildren().addAll(departmentButton, label);
            }

        });

        topBar.getItems().addAll(departmentsButton, exitButton);
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

}