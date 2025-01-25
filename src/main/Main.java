package main;

import departmentStructure.departmentModule.Department;
import equipmentStructure.equipmentModule.Equipment;
import gameStructure.gameModule.Game;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import responderStructure.responderModule.Responder;
import skillStructure.skillModule.Skill;
import stationStructure.stationModule.Station;
import trainingStructure.trainingModule.Training;
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

        Button organizationButton = new Button("Organization");
        topBar.getItems().addAll(organizationButton, exitButton);

        organizationButton.setOnAction(_ -> {
            List<Control> newControls = setBreadcrumbs(leftPanel);
            organizationButtonAction(leftPanel, newControls, game);
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

    public void addPaneEntry(Pane pane, Button button, String text1, String text2, EventHandler<ActionEvent> eventHandler) {
        button.setText(text1);
        button.setAlignment(Pos.CENTER_LEFT);
        button.setMinWidth(200);
        button.setOnAction(eventHandler);
        Label label = new Label(text2);
        HBox hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(button, label);
        pane.getChildren().addAll(hBox);
    }

    private List<Control> setBreadcrumbs(Pane pane, List<Control> controls, Control newControl) {
        resetBreadcrumbs(pane, controls);
        pane.getChildren().add(newControl);
        List<Control> newControls = new ArrayList<>(controls);
        newControls.add(newControl);
        return newControls;
    }

    private List<Control> setBreadcrumbs(Pane pane) {
        List<Control> controls = new ArrayList<>();
        resetBreadcrumbs(pane, controls);
        return controls;
    }

    private void resetBreadcrumbs(Pane pane, List<Control> controls) {
        pane.getChildren().clear();
        pane.getChildren().addAll(controls);
    }

    // List Views

    private void departmentListView(Pane pane, List<Control> controls, List<Department> departments) {
        for (Department department : departments) {
            Button button = new Button();
            EventHandler<ActionEvent> eventHandler = _ -> {
                List<Control> newControls = setBreadcrumbs(pane, controls, button);
                departmentButtonAction(pane, newControls, department);
            };
            addPaneEntry(pane, button, department.toString(), department.getStations().size() + " stations", eventHandler);
        }
    }

    private void stationsListView(Pane pane, List<Control> controls, List<Station> stations) {
        Label stationsSeparator = new Label("----------------------\nStations");
        pane.getChildren().addAll(stationsSeparator);

        for (Station station : stations) {
            Button button = new Button();
            EventHandler<ActionEvent> eventHandler = _ -> {
                List<Control> newControls = setBreadcrumbs(pane, controls, button);
                stationButtonAction(pane, newControls, station);
            };
            addPaneEntry(pane, button, station.toString(), station.getUnits().size() + " units", eventHandler);
        }
    }

    private void unitsListView(Pane pane, List<Control> controls, List<Unit> units) {
        Label unitsSeparator = new Label("----------------------\nUnits");
        pane.getChildren().addAll(unitsSeparator);

        for (Unit unit : units) {
            Button button = new Button();
            EventHandler<ActionEvent> eventHandler = _ -> {
                List<Control> newControls = setBreadcrumbs(pane, controls, button);
                unitButtonAction(pane, newControls, unit);
            };
            addPaneEntry(pane, button, unit.toString(), unit.getResponders().size() + " responders", eventHandler);
        }
    }

    private void respondersListView(Pane pane, List<Control> controls, List<Responder> responders) {
        Label respondersSeparator = new Label("----------------------\nResponders");
        pane.getChildren().addAll(respondersSeparator);

        for (Responder responder : responders) {
            Button button = new Button();
            EventHandler<ActionEvent> eventHandler = _ -> {
                List<Control> newControls = setBreadcrumbs(pane, controls, button);
                responderButtonAction(pane, newControls, responder);
            };
            addPaneEntry(pane, button, responder.toString(), responder.getRank().toString(), eventHandler);
        }
    }

    private void vehiclesListView(Pane pane, List<Control> controls, List<Vehicle> vehicles) {
        Label vehiclesSeparator = new Label("----------------------\nVehicles");
        pane.getChildren().addAll(vehiclesSeparator);

        for (Vehicle vehicle : vehicles) {
            Button button = new Button();
            EventHandler<ActionEvent> eventHandler = _ -> {
                setBreadcrumbs(pane, controls, button);
                vehicleButtonAction(pane);
            };
            addPaneEntry(pane, button, vehicle.toString(), "Integrity: " + String.format("%.0f%%", vehicle.getIntegrity() * 100) + " - Condition: " + String.format("%.0f%%", vehicle.getCondition() * 100), eventHandler);
        }
    }

    private void equipmentsListView(Pane pane, List<Control> controls, List<Equipment> equipments) {
        Label equipmentsSeparator = new Label("----------------------\nEquipments");
        pane.getChildren().addAll(equipmentsSeparator);

        for (Equipment equipment : equipments) {
            Button button = new Button();
            EventHandler<ActionEvent> eventHandler = _ -> {
                setBreadcrumbs(pane, controls, button);
                equipmentButtonAction(pane);
            };
            addPaneEntry(pane, button, equipment.toString(), "Integrity: " + String.format("%.0f%%", equipment.getIntegrity() * 100) + " - Condition: " + String.format("%.0f%%", equipment.getCondition() * 100), eventHandler);
        }
    }

    private void skillsListView(Pane pane, List<Control> controls, List<Skill> skills) {
        Label skillsSeparator = new Label("----------------------\nSkills");
        pane.getChildren().addAll(skillsSeparator);

        for (Skill skill : skills) {
            Button button = new Button();
            EventHandler<ActionEvent> eventHandler = _ -> {
                setBreadcrumbs(pane, controls, button);
                skillButtonAction(pane);
            };
            addPaneEntry(pane, button, skill.toString(), "", eventHandler);
        }
    }

    private void trainingsListView(Pane pane, List<Control> controls, List<Training> trainings) {
        Label trainingsSeparator = new Label("----------------------\nTrainings");
        pane.getChildren().addAll(trainingsSeparator);

        for (Training training : trainings) {
            Button button = new Button();
            EventHandler<ActionEvent> eventHandler = _ -> {
                setBreadcrumbs(pane, controls, button);
                trainingButtonAction(pane);
            };
            addPaneEntry(pane, button, training.toString(), "", eventHandler);
        }
    }

    // Detail Views

    private void organizationView(Pane pane, List<Control> controls, Game game) {
        departmentListView(pane, controls, game.departmentManager.getDepartments());
    }

    private void departmentView(Pane pane, List<Control> controls, Department department) {
        stationsListView(pane, controls, department.getStations());
    }

    private void stationView(Pane pane, List<Control> controls, Station station) {
        unitsListView(pane, controls, station.getUnits());
        respondersListView(pane, controls, station.getResponders());
        vehiclesListView(pane, controls, station.getVehicles());
    }

    private void unitView(Pane pane, List<Control> controls, Unit unit) {
        respondersListView(pane, controls, unit.getResponders());
        vehiclesListView(pane, controls, unit.getVehicles());
        equipmentsListView(pane, controls, unit.getEquipments());
    }

    private void responderView(Pane pane, List<Control> controls, Responder responder) {
        skillsListView(pane, controls, responder.getSkills());
        trainingsListView(pane, controls, responder.getTrainings());
    }

    private void vehicleView(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

    private void equipmentView(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

    private void skillView(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

    private void trainingView(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

    // Button Actions

    private void organizationButtonAction(VBox leftPanel, List<Control> controls, Game game) {
        organizationView(leftPanel, controls, game);
    }

    private void departmentButtonAction(Pane pane, List<Control> controls, Department department) {
        departmentView(pane, controls, department);
    }

    private void stationButtonAction(Pane pane, List<Control> controls, Station station) {
        stationView(pane, controls, station);
    }

    private void unitButtonAction(Pane pane, List<Control> controls, Unit unit) {
        unitView(pane, controls, unit);
    }

    private void responderButtonAction(Pane pane, List<Control> controls, Responder responder) {
        responderView(pane, controls, responder);
    }

    private void vehicleButtonAction(Pane pane) {
        vehicleView(pane);
    }

    private void equipmentButtonAction(Pane pane) {
        equipmentView(pane);
    }

    private void skillButtonAction(Pane pane) {
        skillView(pane);
    }

    private void trainingButtonAction(Pane pane) {
        trainingView(pane);
    }

}