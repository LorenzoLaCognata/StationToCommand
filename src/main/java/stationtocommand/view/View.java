package stationtocommand.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
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
import stationtocommand.view.mainStructure.DispatchView;
import stationtocommand.view.mainStructure.NavigationPanel;
import stationtocommand.view.mainStructure.UtilsView;
import stationtocommand.view.mainStructure.WorldMap;
import stationtocommand.view.organizationStructure.OrganizationView;
import stationtocommand.view.organizationStructure.ResponderView;
import stationtocommand.view.organizationStructure.StationView;
import stationtocommand.view.organizationStructure.VehicleView;

import java.util.List;

public class View {

    private Controller controller;
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

    public WorldMap getWorldMap() {
        return worldMap;
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
        this.dispatchView = new DispatchView(utilsView);
        organizationButtonHandler("Organization", controller.getModel().getDepartmentManager().getDepartments());
    }

    public void generateHomePage(List<Department> departments, List<Mission> missions) {

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

        organizationButton.setOnAction(_ -> organizationButtonHandler(organizationButton.getText(), departments));
        toolbar.getItems().addAll(organizationButton);

        dispatchButton.setOnAction(_ -> dispatchButtonHandler(dispatchButton.getText(), missions));
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
        breadCrumbActions(departments, missions);

        Timeline userInterfaceUpdater = new Timeline(new KeyFrame(Duration.seconds(1), _ -> refreshUserInterface()));
        userInterfaceUpdater.setCycleCount(Animation.INDEFINITE);
        userInterfaceUpdater.play();

    }

    private void breadCrumbActions(List<Department> departments, List<Mission> missions) {
        breadCrumbBar.setOnCrumbAction(event -> {
            Object selectedObject = event.getSelectedCrumb().getValue();

            if (selectedObject instanceof String string) {
                switch (string) {
                    case "Organization":
                        organizationButtonHandler("Organization", departments);
                        break;
                    case "Dispatch":
                        dispatchButtonHandler("Dispatch", missions);
                        break;
                }
            }
            else if (selectedObject instanceof Department department) {
                worldMap.setMapElementsNotVisible();
                utilsView.resetBreadCrumbBar(breadCrumbBar);
                organizationView.getDepartmentView(department).showDepartment(this);
            }
            else if (selectedObject instanceof Station station) {
                Department department = station.getDepartment();
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                organizationView.getDepartmentView(department).getStationView(station).showStation(this);
            }
            else if (selectedObject instanceof Unit unit) {
                Station station = unit.getStation();
                Department department = station.getDepartment();
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                organizationView.getDepartmentView(department).getStationView(station).getUnitView(unit).showUnit(this);
            }
            else if (selectedObject instanceof Responder responder) {
                Unit unit = responder.getUnitLink().getUnit();
                Station station = unit.getStation();
                Department department = station.getDepartment();
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                StationView stationView = organizationView.getDepartmentView(department).getStationView(station);
                ResponderView responderView = stationView.getResponderView(responder);
                responderView.showResponder(this, stationView.getResponderIcons(), stationView.getResponderNodes().get(responderView.getResponder()));

            }
            else if (selectedObject instanceof Vehicle vehicle) {
                Unit unit = vehicle.getUnitLink().getUnit();
                Station station = unit.getStation();
                Department department = station.getDepartment();
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                StationView stationView = organizationView.getDepartmentView(department).getStationView(station);
                VehicleView vehicleView = stationView.getVehicleView(vehicle);
                vehicleView.showVehicle(this, stationView.getVehicleIcons(), stationView.getVehicleNodes().get(vehicleView.getVehicle()));
            }
            else if (selectedObject instanceof Mission mission) {
                utilsView.resetBreadCrumbBar(breadCrumbBar);
                dispatchView.getMissionListView().getMissionView().show(this, mission);
            }
            else if (selectedObject instanceof MissionDepartmentLink missionDepartmentLink) {
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                dispatchView.getMissionListView().getMissionView().getMissionDepartmentListView().getMissionDepartmentView().show(this, missionDepartmentLink);
            }
            else if (selectedObject instanceof MissionStationLink missionStationLink) {
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                dispatchView.getMissionListView().getMissionView().getMissionDepartmentListView().getMissionDepartmentView().getMissionStationListView().getMissionStationView().show(this, missionStationLink);
            }
            else if (selectedObject instanceof MissionUnitLink missionUnitLink) {
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                dispatchView.getMissionListView().getMissionView().getMissionDepartmentListView().getMissionDepartmentView().getMissionStationListView().getMissionStationView().getMissionUnitListView().getMissionUnitView().show(this, missionUnitLink);
            }
            else if (selectedObject instanceof MissionResponderLink missionResponderLink) {
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                dispatchView.getMissionListView().getMissionView().getMissionDepartmentListView().getMissionDepartmentView().getMissionStationListView().getMissionStationView().getMissionResponderListView().getMissionResponderView().show(this, missionResponderLink);
            }
            else if (selectedObject instanceof MissionVehicleLink missionVehicleLink) {
                utilsView.addBreadCrumb(breadCrumbBar, selectedObject);
                dispatchView.getMissionListView().getMissionView().getMissionDepartmentListView().getMissionDepartmentView().getMissionStationListView().getMissionStationView().getMissionVehicleListView().getMissionVehicleView().show(this, missionVehicleLink);
            }
        });
    }

    public void organizationButtonHandler(String buttonText, List<Department> departments) {
        viewRunnable = () -> organizationButtonHandler(buttonText, departments);
        navigationPanel.clearAll();
        worldMap.clear();
        utilsView.resetBreadCrumbBar(breadCrumbBar);
        utilsView.addBreadCrumb(breadCrumbBar, buttonText);
        organizationView.showOrganization(this);
    }

    public void dispatchButtonHandler(String buttonText, List<Mission> missions) {
        viewRunnable = () -> dispatchButtonHandler(buttonText, missions);
        navigationPanel.clearAll();
        worldMap.clear();
        utilsView.resetBreadCrumbBar(breadCrumbBar);
        utilsView.addBreadCrumb(breadCrumbBar, buttonText);
        dispatchView.show(this, missions);
    }

    public void refreshUserInterface() {
        if (viewRunnable != null) {
            viewRunnable.run();
        }
    }

}
