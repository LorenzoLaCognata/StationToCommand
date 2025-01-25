package main;

import departmentStructure.departmentModule.Department;
import gameStructure.gameModule.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
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

import java.util.ArrayList;
import java.util.List;

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
        topBar.getItems().addAll(departmentsButton, exitButton);

        departmentsButton.setOnAction(_ -> {
            resetBreadcrumbs(leftPanel, new ArrayList<>());
            organizationView(leftPanel, game.departmentManager.getDepartments(), game);
        });

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

    private static void organizationView(Pane pane, List<Department> departments, Game game) {
        for (Department department : departments) {
            Button departmentButton = new Button(department.toString());
            Label departmentLabel = new Label("\t" + department.getStations().size() + " stations");
            pane.getChildren().addAll(departmentButton, departmentLabel);
            departmentButton.setOnAction(_ -> {
                List<Control> controls = List.of(departmentButton);
                resetBreadcrumbs(pane, controls);
                departmentView(pane, controls, department.getStations(), game);
            });
        }
    }

    // TODO: change passing of entire Game object
    private static void departmentView(Pane pane, List<Control> controls, List<Station> stations, Game game) {
        Label stationsSeparator = new Label("----------------------\nStations");
        pane.getChildren().addAll(stationsSeparator);

        for (Station station : stations) {
            Button stationButton = new Button(station.toString());
            Label stationLabel = new Label("\t" + station.getUnits().size() + " units");
            pane.getChildren().addAll(stationButton, stationLabel);
            stationButton.setOnAction(_ -> {
                List<Control> newControls = new ArrayList<>(controls);
                newControls.add(stationButton);
                resetBreadcrumbs(pane, newControls);
                stationView(pane, newControls, game, station);
            });
        }
    }

    private static void stationView(Pane pane, List<Control> controls, Game game, Station station) {
        stationUnitsListView(pane, controls, station.getUnits(), game);
        stationVehiclesListView(pane, controls, game.vehicleManager.getVehicles(station));
    }

    // TODO: change passing of entire Game object
    private static void stationUnitsListView(Pane pane, List<Control> controls, List<Unit> units, Game game) {
        Label unitsSeparator = new Label("----------------------\nUnits");
        pane.getChildren().addAll(unitsSeparator);

        for (Unit unit : units) {
            Button unitButton = new Button(unit.toString());
            Label unitLabel = new Label("\t" + game.responderManager.getResponders(unit).size() + " responders");
            pane.getChildren().addAll(unitButton, unitLabel);
            unitButton.setOnAction(_ -> {
                List<Control> newControls = new ArrayList<>(controls);
                newControls.add(unitButton);
                resetBreadcrumbs(pane, newControls);
                unitView(pane, newControls, game.responderManager.getResponders(unit), game.vehicleManager.getVehicles(unit));
            });
        }
    }

    private static void stationVehiclesListView(Pane pane, List<Control> controls, List<Vehicle> vehicles) {
        Label vehiclesSeparator = new Label("----------------------\nVehicles");
        pane.getChildren().addAll(vehiclesSeparator);

        for (Vehicle vehicle : vehicles) {
            Button vehicleButton = new Button(vehicle.toString());
            Label vehicleLabel = new Label("\tIntegrity: " + String.format("%.0f%%", vehicle.getIntegrity() * 100) + " - Condition: " + String.format("%.0f%%", vehicle.getCondition() * 100));
            pane.getChildren().addAll(vehicleButton, vehicleLabel);
            vehicleButton.setOnAction(_ -> {
                List<Control> newControls = new ArrayList<>(controls);
                newControls.add(vehicleButton);
                resetBreadcrumbs(pane, newControls);
                vehicleView(pane);
            });
        }
    }

    private static void unitView(Pane pane, List<Control> controls, List<Responder> responders, List<Vehicle> vehicles) {
        unitRespondersListView(pane, controls, responders);
        unitVehiclesListView(pane, controls, vehicles);
    }

    private static void unitRespondersListView(Pane pane, List<Control> controls, List<Responder> responders) {
        Label respondersSeparator = new Label("----------------------\nResponders");
        pane.getChildren().addAll(respondersSeparator);

        for (Responder responder : responders) {
            Button responderButton = new Button(responder.toString());
            Label responderLabel = new Label("\t" + responder.getRank().toString());
            pane.getChildren().addAll(responderButton, responderLabel);
            responderButton.setOnAction(_ -> {
                List<Control> newControls = new ArrayList<>(controls);
                newControls.add(responderButton);
                resetBreadcrumbs(pane, newControls);
                responderView(pane, newControls, responder);
            });
        }
    }

    private static void responderView(Pane pane, List<Control> controls, Responder responder) {
        responderSkillsListView(pane, controls, responder.getSkills());
    }

    private static void unitVehiclesListView(Pane pane, List<Control> controls, List<Vehicle> vehicles) {
        Label vehiclesSeparator = new Label("----------------------\nVehicles");
        pane.getChildren().addAll(vehiclesSeparator);

        for (Vehicle vehicle : vehicles) {
            Button vehicleButton = new Button(vehicle.toString());
            Label vehicleLabel = new Label("\tIntegrity: " + String.format("%.0f%%", vehicle.getIntegrity() * 100) + " - Condition: " + String.format("%.0f%%", vehicle.getCondition() * 100));
            pane.getChildren().addAll(vehicleButton, vehicleLabel);
            vehicleButton.setOnAction(_ -> {
                List<Control> newControls = new ArrayList<>(controls);
                newControls.add(vehicleButton);
                resetBreadcrumbs(pane, newControls);
                vehicleView(pane);
            });
        }
    }

    private static void responderSkillsListView(Pane pane, List<Control> controls, List<Skill> skills) {
        Label skillsSeparator = new Label("----------------------\nSkills");
        pane.getChildren().addAll(skillsSeparator);

        for (Skill skill : skills) {
            Button skillButton = new Button(skill.toString());
            Label skillLabel = new Label("\t" + skill);
            pane.getChildren().addAll(skillButton, skillLabel);
            skillButton.setOnAction(_ -> {
                List<Control> newControls = new ArrayList<>(controls);
                newControls.add(skillButton);
                resetBreadcrumbs(pane, newControls);
                skillView(pane);
            });
        }
    }

    private static void resetBreadcrumbs(Pane pane, List<Control> controls) {
        pane.getChildren().clear();
        pane.getChildren().addAll(controls);
    }

    private static void vehicleView(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

    private static void skillView(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

}