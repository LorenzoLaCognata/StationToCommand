package main;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.departmentStructure.departmentModule.Department;
import model.equipmentStructure.equipmentModule.Equipment;
import model.gameStructure.gameModule.Game;
import model.responderStructure.responderModule.Responder;
import model.skillStructure.skillModule.Skill;
import model.stationStructure.stationModule.Station;
import model.trainingStructure.trainingModule.Training;
import model.unitStructure.unitModule.Unit;
import model.vehicleStructure.vehicleModule.Vehicle;
import view.userInterfaceStructure.userInterfaceModule.UserInterface;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private final Game game = new Game();
    private UserInterface userInterface;

    @Override
    public void start(Stage primaryStage) {

        userInterface =  new UserInterface(this);

        Scene scene = new Scene(userInterface.getParentScene(), 800, 600);

        primaryStage.setTitle("Station To Command");
        primaryStage.setMaximized(true);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(scene);
        primaryStage.show();

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

    private void trainingView(Pane pane) {
        Label separator = new Label("----------------------\n");
        pane.getChildren().addAll(separator);
    }

    // Button Actions

    // TODO: check if it's possible to remove the Pane parameter
    // TODO: change after moving setBreadcrumbs to UserInterface
    public void organizationButtonAction(Pane pane) {
        List<Control> controls = setBreadcrumbs(pane);
        organizationView(pane, controls, game);
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
        userInterface.getSkillView().skillView(pane);
    }

    private void trainingButtonAction(Pane pane) {
        trainingView(pane);
    }

}