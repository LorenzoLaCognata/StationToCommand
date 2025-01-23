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
import skillStructure.skillModule.Skill;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.Unit;
import vehicleStructure.vehicleModule.Vehicle;

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
                Label departmentLabel = new Label("\t" + department.getStations().size() + " stations");
                leftPanel.getChildren().addAll(departmentButton, departmentLabel);
                departmentButton.setOnAction(_ -> {
                    leftPanel.getChildren().clear();
                    Label stationsSeparator = new Label("----------------------\nStations");
                    leftPanel.getChildren().addAll(departmentButton, stationsSeparator);


                    for (Station station : department.getStations()) {
                        Button stationButton = new Button(station.toString());
                        Label stationLabel = new Label("\t" + station.getUnits().size() + " units");
                        leftPanel.getChildren().addAll(stationButton, stationLabel);
                        stationButton.setOnAction(_ -> {
                            leftPanel.getChildren().clear();
                            Label unitsSeparator = new Label("----------------------\nUnits");
                            leftPanel.getChildren().addAll(departmentButton, stationButton, unitsSeparator);

                            for (Unit unit : station.getUnits()) {
                                Button unitButton = new Button(unit.toString());
                                Label unitLabel = new Label("\t" + game.responderManager.getResponders(unit).size() + " responders");
                                leftPanel.getChildren().addAll(unitButton, unitLabel);
                                unitButton.setOnAction(_ -> {
                                    leftPanel.getChildren().clear();
                                    Label respondersSeparator = new Label("----------------------\nResponders");
                                    leftPanel.getChildren().addAll(departmentButton, stationButton, unitButton, respondersSeparator);

                                    for (Responder responder : game.responderManager.getResponders(unit)) {
                                        Button responderButton = new Button(responder.toString());
                                        Label responderLabel = new Label("\t" + responder.getRank().toString());
                                        leftPanel.getChildren().addAll(responderButton, responderLabel);
                                        responderButton.setOnAction(_ -> {
                                            leftPanel.getChildren().clear();
                                            Label skillsSeparator = new Label("----------------------\nSkills");
                                            leftPanel.getChildren().addAll(departmentButton, stationButton, unitButton, responderButton, skillsSeparator);

                                            for (Skill skill : game.skillManager.getSkillsByResponder(responder)) {
                                                Button skillButton = new Button(skill.toString());
                                                Label skillLabel = new Label("\t" + skill);
                                                leftPanel.getChildren().addAll(skillButton, skillLabel);
                                                skillButton.setOnAction(_ -> {
                                                    leftPanel.getChildren().clear();
                                                    Label separator = new Label("----------------------\n");
                                                    leftPanel.getChildren().addAll(departmentButton, stationButton, unitButton, responderButton, skillButton, separator);
                                                });
                                            }
                                        });
                                    }

                                    Label vehiclesSeparator = new Label("----------------------\nVehicles");
                                    leftPanel.getChildren().addAll(vehiclesSeparator);

                                    for (Vehicle vehicle : game.vehicleManager.getVehicles(unit)) {
                                        Button vehicleButton = new Button(vehicle.toString());
                                        Label vehicleLabel = new Label("\tIntegrity: " + String.format("%.0f%%", vehicle.getIntegrity() * 100) + " - Condition: " + String.format("%.0f%%", vehicle.getCondition() * 100));
                                        leftPanel.getChildren().addAll(vehicleButton, vehicleLabel);
                                        vehicleButton.setOnAction(_ -> {
                                            leftPanel.getChildren().clear();
                                            Label separator = new Label("----------------------\n");
                                            leftPanel.getChildren().addAll(departmentButton, stationButton, unitButton, vehicleButton, separator);
                                        });
                                    }
                                });
                            }

                            Label vehiclesSeparator = new Label("----------------------\nVehicles");
                            leftPanel.getChildren().addAll(vehiclesSeparator);

                            for (Vehicle vehicle : game.vehicleManager.getVehicles(station)) {
                                Button vehicleButton = new Button(vehicle.toString());
                                Label vehicleLabel = new Label("\tIntegrity: " + String.format("%.0f%%", vehicle.getIntegrity() * 100) + " - Condition: " + String.format("%.0f%%", vehicle.getCondition() * 100));
                                leftPanel.getChildren().addAll(vehicleButton, vehicleLabel);
                                vehicleButton.setOnAction(_ -> {
                                    leftPanel.getChildren().clear();
                                    Label separator = new Label("----------------------\n");
                                    leftPanel.getChildren().addAll(departmentButton, stationButton, vehicleButton, separator);
                                });
                            }

                        });
                    }
                });
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