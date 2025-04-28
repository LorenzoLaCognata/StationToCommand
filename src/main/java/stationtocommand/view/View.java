package stationtocommand.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.controller.Controller;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.missionLinkStructure.*;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.dispatchStructure.DispatchView;
import stationtocommand.view.dispatchStructure.MissionView;
import stationtocommand.view.mainStructure.NavigationPanel;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.mainStructure.WorldMap;
import stationtocommand.view.organizationStructure.OrganizationView;
import stationtocommand.view.organizationStructure.ResponderView;
import stationtocommand.view.organizationStructure.StationView;
import stationtocommand.view.organizationStructure.VehicleView;

import java.util.List;

public class View {

    // TODO: change back to private
    public Controller controller;
    private UtilsView utilsView;
    private OrganizationView organizationView;
    private DispatchView dispatchView;
    private final GridPane gridPane = new GridPane();

    private final ToolBar toolbar = new ToolBar();
    private final BreadCrumbBar<Object> breadCrumbBar = new BreadCrumbBar<>();
    private final NavigationPanel navigationPanel = new NavigationPanel();
    private final WorldMap worldMap = new WorldMap();
    private final HBox hud = new HBox(5);

    private final Label gameClockLabel = new Label();

    public static Runnable viewRunnable;

    public View() {
        ScrollPane scrollPane = new ScrollPane(navigationPanel.getContainer());
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        gridPane.add(toolbar, 0, 0, 2, 1);
        gridPane.add(breadCrumbBar, 0, 1, 2, 1);
        gridPane.add(scrollPane, 0, 2, 1, 2);
        gridPane.add(worldMap.getContainer(), 1, 2);
        gridPane.add(hud, 1, 3);

    }

    public OrganizationView getOrganizationView() {
        return organizationView;
    }

    public DispatchView getDispatchView() {
        return dispatchView;
    }

    public BreadCrumbBar<Object> getBreadCrumbBar() {
        return breadCrumbBar;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

    public ToolBar getToolbar() {
        return toolbar;
    }

    public NavigationPanel getNavigationPanel() {
        return navigationPanel;
    }

    public HBox getTitlePane() {
        return navigationPanel.getTitlePane();
    }

    public HBox getButtonsPane() {
        return navigationPanel.getButtonsPane();
    }

    public VBox getDetailsPane() {
        return navigationPanel.getDetailsPane();
    }

    public void clearNavigationPanel() {
        navigationPanel.getTitlePane().getChildren().clear();
        navigationPanel.getButtonsPane().getChildren().clear();
        navigationPanel.getDetailsPane().getChildren().clear();
    }

    public void clearDetailsPane() {
        navigationPanel.getDetailsPane().getChildren().clear();
    }

    public WorldMap getWorldMap() {
        return worldMap;
    }

    public void addToMap(Node node) {
        //System.out.println("Add 1 - " + node + " - " + ((ImageView) node).getImage().getUrl());
        if (!worldMap.getMapElementsLayer().getChildren().contains(node)) {
            //System.out.println("Add 2 - " + node + " - " + ((ImageView) node).getImage().getUrl());
            worldMap.getMapElementsLayer().getChildren().add(node);
            //System.out.println("Add 3 - " + node + " - " + ((ImageView) node).getImage().getUrl());
        }
        else {
            //System.out.println("Add 4 - " + node + " - " + ((ImageView) node).getImage().getUrl());
            node.setVisible(true);
            //System.out.println("Add 5 - " + node + " - " + ((ImageView) node).getImage().getUrl());
        }
        //System.out.println("Add 6 - " + node + " - " + ((ImageView) node).getImage().getUrl());
    }

    // TODO: fix and remove this version
    public void addToMapLOGGING(Node node) {
        System.out.println("Add to Map 1 - " + node + " - " + ((ImageView) node).getImage().getUrl());
        if (!worldMap.getMapElementsLayer().getChildren().contains(node)) {
            System.out.println("Add to Map 2 - " + node + " - " + ((ImageView) node).getImage().getUrl());
            worldMap.getMapElementsLayer().getChildren().add(node);
            System.out.println("Add to Map 3 - " + node + " - " + ((ImageView) node).getImage().getUrl());
        }
        else {
            System.out.println("Add to Map 4 - " + node + " - " + ((ImageView) node).getImage().getUrl());
            node.setVisible(true);
            System.out.println("Add to Map 5 - " + node + " - " + ((ImageView) node).getImage().getUrl());
        }
        System.out.println("Add to Map 6 - " + node + " - " + ((ImageView) node).getImage().getUrl());
    }

    // TODO: fix and remove this version
    public void addToMapDISABLED(Node node) {
        //System.out.println("Add Disabled 1 - " + node + " - " + ((ImageView) node).getImage().getUrl());
        if (!worldMap.getMapElementsLayer().getChildren().contains(node)) {
            //System.out.println("Add Disabled 2 - " + node + " - " + ((ImageView) node).getImage().getUrl());
            //worldMap.getMapElementsLayer().getChildren().add(node);
            //System.out.println("Add Disabled 3 - " + node + " - " + ((ImageView) node).getImage().getUrl());
        }
        else {
            //System.out.println("Add Disabled 4 - " + node + " - " + ((ImageView) node).getImage().getUrl());
            //node.setVisible(true);
            //System.out.println("Add Disabled 5 - " + node + " - " + ((ImageView) node).getImage().getUrl());
        }
        //System.out.println("Add Disabled 6 - " + node + " - " + ((ImageView) node).getImage().getUrl());
    }

    public void hideMap() {
        for (Node node : worldMap.getMapElementsLayer().getChildren()) {
            node.setVisible(false);
        }
    }

    public HBox getHud() {
        return hud;
    }

    public Label getGameClockLabel() {
        return gameClockLabel;
    }

    public UtilsView getUtilsView() {
        return utilsView;
    }

    public void initialize(Controller controller) {
        this.controller = controller;
        this.utilsView = new UtilsView();
        this.organizationView = new OrganizationView(controller.getModel().getDepartmentManager().getDepartments(), controller.getView(), utilsView);
        this.dispatchView = new DispatchView(controller.getModel().getMissionManager().getMissions(), controller.getView(), utilsView);
        organizationButtonHandler("Organization");
    }

    public void generateHomePage(List<Mission> missions) {

        hud.setSpacing(15);
        hud.setPadding(new Insets(15));
        hud.setStyle("-fx-background-color: rgba(20, 20, 20, 0.9); -fx-border-radius: 10;");

        VBox vbox = new VBox(5);
        Responder responder = controller.getModel().getResponderManager().getPlayer();
        utilsView.addBodyLabel(vbox, responder.toString());
        utilsView.addBodyLabel(vbox, responder.getRank().toString());
        hud.getChildren().add(vbox);

        Button speed0Button = new Button("⏸");
        Button speed1Button = new Button("›");
        Button speed2Button = new Button("››");
        Button speed3Button = new Button("›››");

        Button organizationButton = new Button("Organization");
        Button dispatchButton = new Button("Dispatch");

        ColumnConstraints firstCol = new ColumnConstraints();
        firstCol.setHgrow(Priority.ALWAYS);

        ColumnConstraints secondCol = new ColumnConstraints();
        secondCol.setHgrow(Priority.NEVER);
        secondCol.setMinWidth(WorldMap.MAP_WIDTH);
        secondCol.setMaxWidth(WorldMap.MAP_WIDTH);

        gridPane.getColumnConstraints().addAll(firstCol, secondCol);

        RowConstraints firstRow = new RowConstraints();
        firstRow.setVgrow(Priority.NEVER);
        firstRow.setMinHeight(60);
        firstRow.setMaxHeight(60);

        RowConstraints secondRow = new RowConstraints();
        secondRow.setVgrow(Priority.NEVER);
        secondRow.setMinHeight(40);
        secondRow.setMaxHeight(40);

        RowConstraints thirdRow = new RowConstraints();
        thirdRow.setVgrow(Priority.NEVER);
        thirdRow.setMinHeight(WorldMap.MAP_HEIGHT);
        thirdRow.setMaxHeight(WorldMap.MAP_HEIGHT);

        RowConstraints fourthRow = new RowConstraints();
        fourthRow.setVgrow(Priority.ALWAYS);

        gridPane.getRowConstraints().addAll(firstRow, secondRow, thirdRow, fourthRow);

        organizationButton.setOnAction(_ -> organizationButtonHandler(organizationButton.getText()));
        toolbar.getItems().addAll(organizationButton);

        dispatchButton.setOnAction(_ -> dispatchButtonHandler(dispatchButton.getText()));
        toolbar.getItems().addAll(dispatchButton);

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(_ -> {
            controller.getScheduler().stopGameClock();
            Platform.exit();
            System.exit(0);
        });
        toolbar.getItems().addAll(quitButton);

        toolbar.getItems().add(gameClockLabel);

        speed0Button.setOnAction(_ -> controller.getScheduler().stopGameClock());
        speed1Button.setOnAction(_ -> {
            controller.getScheduler().startGameClock();
            controller.getScheduler().setTimeScale(30.0);
        });
        speed2Button.setOnAction(_ -> {
            controller.getScheduler().startGameClock();
            controller.getScheduler().setTimeScale(300.0);
        });
        speed3Button.setOnAction(_ -> {
            controller.getScheduler().startGameClock();
            controller.getScheduler().setTimeScale(900.0);
        });
        toolbar.getItems().addAll(speed0Button, speed1Button, speed2Button, speed3Button);

        breadCrumbBar.setStyle("-fx-background-color: #222; -fx-padding: 5px;");
        breadCrumbActions(missions);

        Timeline userInterfaceUpdater = new Timeline(new KeyFrame(Duration.seconds(1), _ -> refreshUserInterface()));
        userInterfaceUpdater.setCycleCount(Animation.INDEFINITE);
        userInterfaceUpdater.play();

    }

    private void breadCrumbActions(List<Mission> missions) {
        breadCrumbBar.setOnCrumbAction(event -> {
            Object selectedObject = event.getSelectedCrumb().getValue();

            if (selectedObject instanceof String string) {
                switch (string) {
                    case "Organization":
                        organizationButtonHandler("Organization");
                        break;
                    case "Dispatch":
                        dispatchButtonHandler("Dispatch");
                        break;
                }
            }
            else if (selectedObject instanceof Department department) {
                hideMap();
                utilsView.resetBreadCrumbBar(breadCrumbBar);
                organizationView.getDepartmentView(department).show(this);
            }
            else if (selectedObject instanceof Station station) {
                Department department = station.getDepartment();
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                organizationView.getDepartmentView(department).getStationView(station).show(this);
            }
            else if (selectedObject instanceof Unit unit) {
                Station station = unit.getStation();
                Department department = station.getDepartment();
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                organizationView.getDepartmentView(department).getStationView(station).getUnitView(unit).show(this);
            }
            else if (selectedObject instanceof Responder responder) {
                Unit unit = responder.getUnitLink().getUnit();
                Station station = unit.getStation();
                Department department = station.getDepartment();
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                StationView stationView = organizationView.getDepartmentView(department).getStationView(station);
                ResponderView responderView = stationView.getResponderView(responder);
                responderView.show(this);

            }
            else if (selectedObject instanceof Vehicle vehicle) {
                Unit unit = vehicle.getUnitLink().getUnit();
                Station station = unit.getStation();
                Department department = station.getDepartment();
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                StationView stationView = organizationView.getDepartmentView(department).getStationView(station);
                VehicleView vehicleView = stationView.getVehicleView(vehicle);
                vehicleView.show(this);
            }
            else if (selectedObject instanceof Mission mission) {
                MissionView missionView = dispatchView.getMissionView(mission);
                utilsView.resetBreadCrumbBar(breadCrumbBar);
                missionView.show(this);
            }
            else if (selectedObject instanceof MissionDepartmentLink missionDepartmentLink) {
                Mission mission = missionDepartmentLink.getMission();
                MissionView missionView = dispatchView.getMissionView(mission);
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                missionView.getMissionDepartmentView(missionDepartmentLink).show(this);
            }
            else if (selectedObject instanceof MissionStationLink missionStationLink) {
                Mission mission = missionStationLink.getMission();
                MissionView missionView = dispatchView.getMissionView(mission);
                MissionDepartmentLink missionDepartmentLink = mission.getDepartmentLink(missionStationLink.getStation().getDepartment());
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                missionView.getMissionDepartmentView(missionDepartmentLink).getMissionStationView(missionStationLink).show(this);
            }
            else if (selectedObject instanceof MissionUnitLink missionUnitLink) {
                Mission mission = missionUnitLink.getMission();
                MissionView missionView = dispatchView.getMissionView(mission);
                MissionDepartmentLink missionDepartmentLink = mission.getDepartmentLink(missionUnitLink.getUnit().getStation().getDepartment());
                MissionStationLink missionStationLink = missionDepartmentLink.getStationLink(missionUnitLink.getUnit().getStation());
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                missionView.getMissionDepartmentView(missionDepartmentLink).getMissionStationView(missionStationLink).getMissionUnitView(missionUnitLink).show(this);
            }
            else if (selectedObject instanceof MissionResponderLink missionResponderLink) {
                Mission mission = missionResponderLink.getMission();
                MissionView missionView = dispatchView.getMissionView(mission);
                MissionDepartmentLink missionDepartmentLink = mission.getDepartmentLink(missionResponderLink.getResponder().getUnitLink().getUnit().getStation().getDepartment());
                MissionStationLink missionStationLink = missionDepartmentLink.getStationLink(missionResponderLink.getResponder().getUnitLink().getUnit().getStation());
                MissionUnitLink missionUnitLink = missionStationLink.getUnitLink(missionResponderLink.getResponder().getUnitLink().getUnit());
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                missionView.getMissionDepartmentView(missionDepartmentLink).getMissionStationView(missionStationLink).getMissionUnitView(missionUnitLink).getMissionResponderView(missionResponderLink).show(this);
            }
            else if (selectedObject instanceof MissionVehicleLink missionVehicleLink) {
                Mission mission = missionVehicleLink.getMission();
                MissionView missionView = dispatchView.getMissionView(mission);
                MissionDepartmentLink missionDepartmentLink = mission.getDepartmentLink(missionVehicleLink.getVehicle().getUnitLink().getUnit().getStation().getDepartment());
                MissionStationLink missionStationLink = missionDepartmentLink.getStationLink(missionVehicleLink.getVehicle().getUnitLink().getUnit().getStation());
                MissionUnitLink missionUnitLink = missionStationLink.getUnitLink(missionVehicleLink.getVehicle().getUnitLink().getUnit());
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                missionView.getMissionDepartmentView(missionDepartmentLink).getMissionStationView(missionStationLink).getMissionUnitView(missionUnitLink).getMissionVehicleView(missionVehicleLink).show(this);
            }
        });
    }

    public void organizationButtonHandler(String buttonText) {
        viewRunnable = () -> organizationButtonHandler(buttonText);
        clearNavigationPanel();
        hideMap();
        utilsView.resetBreadCrumbBar(breadCrumbBar);
        utilsView.addBreadCrumb(breadCrumbBar, buttonText);
        organizationView.show(this);
    }

    public void dispatchButtonHandler(String buttonText) {
        viewRunnable = () -> dispatchButtonHandler(buttonText);
        clearNavigationPanel();
        hideMap();
        utilsView.resetBreadCrumbBar(breadCrumbBar);
        utilsView.addBreadCrumb(breadCrumbBar, buttonText);
        dispatchView.show(this);
    }

    public void refreshUserInterface() {
        if (viewRunnable != null) {
            viewRunnable.run();
        }
    }

}
