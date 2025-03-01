package stationtocommand.view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.controller.Controller;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.equipmentStructure.Equipment;
import stationtocommand.model.missionLinkStructure.*;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.view.mainStructure.DispatchView;
import stationtocommand.view.mainStructure.OrganizationView;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;
import java.util.Objects;

public class View {

    public static float MAP_WIDTH = 1400.0f;
    public static float MAP_HEIGHT = 900.0f;

    private Controller controller;
    private UtilsView utilsView;
    private OrganizationView organizationView;
    private DispatchView dispatchView;
    private final GridPane gridPane = new GridPane();

    private final ToolBar toolbar = new ToolBar();
    private final VBox navigationPanel = new VBox(5);
    private final BreadCrumbBar<Object> breadcrumb = new BreadCrumbBar<>();
    private final StackPane worldMap = new StackPane();
    private final HBox hud = new HBox(5);

    private final Label gameClockLabel = new Label();
    private final Pane mapElementsLayer = new Pane();

    public static Runnable viewRunnable;

    public View() {
        gridPane.add(toolbar, 0, 0, 2, 1);
        gridPane.add(breadcrumb, 0, 1, 2, 1);
        gridPane.add(navigationPanel, 0, 2, 1, 2);
        gridPane.add(worldMap, 1, 2);
        gridPane.add(hud, 1, 3);

    }

    public BreadCrumbBar<Object> getBreadcrumb() {
        return breadcrumb;
    }

    public GridPane getGridPane() {
        return gridPane;
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
        this.organizationView = new OrganizationView(utilsView);
        this.dispatchView = new DispatchView(utilsView);
    }

    public void generateHomePage(List<Department> departments, List<Mission> missions) {

        navigationPanel.setSpacing(15);
        navigationPanel.setPadding(new Insets(15));
        navigationPanel.setStyle("-fx-background-color: rgba(20, 20, 20, 0.9); -fx-border-radius: 10;");

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
        secondCol.setMinWidth(MAP_WIDTH);
        secondCol.setMaxWidth(MAP_WIDTH);

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
        thirdRow.setMinHeight(MAP_HEIGHT);
        thirdRow.setMaxHeight(MAP_HEIGHT);

        RowConstraints fourthRow = new RowConstraints();
        fourthRow.setVgrow(Priority.ALWAYS);

        gridPane.getRowConstraints().addAll(firstRow, secondRow, thirdRow, fourthRow);

        ImageView mapView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/images/map-light.png")).toExternalForm()));
        mapView.setFitWidth(MAP_WIDTH);
        mapView.setFitHeight(MAP_HEIGHT);
        mapView.setOpacity(0.60);
        mapView.setPreserveRatio(true);

        Pane mapBackgroundLayer = new Pane();
        mapBackgroundLayer.getChildren().add(mapView);

        organizationButton.setOnAction(_ -> organizationButtonHandler(organizationButton.getText(), departments));
        toolbar.getItems().addAll(organizationButton);

        dispatchButton.setOnAction(_ -> dispatchButtonHandler(dispatchButton.getText(), missions));
        toolbar.getItems().addAll(dispatchButton);

        Button quitButton = new Button("Quit");
        quitButton.setOnAction(_ -> {
            controller.getScheduler().stopGameClock();
            Platform.exit();
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

        breadcrumb.setStyle("-fx-background-color: #222; -fx-padding: 5px;");
        breadCrumbActions(departments, missions);

        worldMap.setStyle("-fx-background-color: #ffffff;");
        worldMap.getChildren().addAll(mapBackgroundLayer, mapElementsLayer);

        Timeline userInterfaceUpdater = new Timeline(new KeyFrame(Duration.seconds(1), _ -> refreshUserInterface()));
        userInterfaceUpdater.setCycleCount(Animation.INDEFINITE);
        userInterfaceUpdater.play();

    }

    private void breadCrumbActions(List<Department> departments, List<Mission> missions) {
        breadcrumb.setOnCrumbAction(event -> {
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
            else if (selectedObject instanceof Department) {
                utilsView.clearPane(mapElementsLayer);
                utilsView.resetBreadCrumbBar(breadcrumb);
                organizationView.getDepartmentListView().getDepartmentView().show(breadcrumb, navigationPanel, mapElementsLayer, (Department) selectedObject);
            }
            else if (selectedObject instanceof Station) {
                utilsView.addBreadCrumb(breadcrumb, selectedObject);
                organizationView.getDepartmentListView().getDepartmentView().getStationListView().getStationView().show(breadcrumb, navigationPanel, mapElementsLayer, (Station) selectedObject);
            }
            else if (selectedObject instanceof Unit) {
                utilsView.addBreadCrumb(breadcrumb, selectedObject);
                organizationView.getDepartmentListView().getDepartmentView().getStationListView().getStationView().getUnitListView().getUnitView().show(breadcrumb, navigationPanel, (Unit) selectedObject);
            }
            else if (selectedObject instanceof Responder) {
                utilsView.addBreadCrumb(breadcrumb, selectedObject);
                organizationView.getDepartmentListView().getDepartmentView().getStationListView().getStationView().getUnitListView().getUnitView().getResponderListView().getResponderView().show(breadcrumb, navigationPanel, (Responder) selectedObject);
            }
            else if (selectedObject instanceof Vehicle) {
                utilsView.addBreadCrumb(breadcrumb, selectedObject);
                organizationView.getDepartmentListView().getDepartmentView().getStationListView().getStationView().getUnitListView().getUnitView().getVehicleListView().getVehicleView().show(breadcrumb, navigationPanel, (Vehicle) selectedObject);
            }
            else if (selectedObject instanceof Equipment) {
                utilsView.addBreadCrumb(breadcrumb, selectedObject);
                organizationView.getDepartmentListView().getDepartmentView().getStationListView().getStationView().getUnitListView().getUnitView().getEquipmentListView().getEquipmentView().show(breadcrumb, navigationPanel, (Equipment) selectedObject);
            }
            else if (selectedObject instanceof Mission) {
                utilsView.resetBreadCrumbBar(breadcrumb);
                dispatchView.getMissionListView().getMissionView().show(breadcrumb, navigationPanel, mapElementsLayer, (Mission) selectedObject);
            }
            else if (selectedObject instanceof MissionDepartmentLink) {
                utilsView.addBreadCrumb(breadcrumb, selectedObject);
                dispatchView.getMissionListView().getMissionView().getMissionDepartmentListView().getMissionDepartmentView().show(breadcrumb, navigationPanel, mapElementsLayer, (MissionDepartmentLink) selectedObject);
            }
            else if (selectedObject instanceof MissionStationLink) {
                utilsView.addBreadCrumb(breadcrumb, selectedObject);
                dispatchView.getMissionListView().getMissionView().getMissionDepartmentListView().getMissionDepartmentView().getMissionStationListView().getMissionStationView().show(breadcrumb, navigationPanel, mapElementsLayer, (MissionStationLink) selectedObject);
            }
            else if (selectedObject instanceof MissionUnitLink) {
                utilsView.addBreadCrumb(breadcrumb, selectedObject);
                dispatchView.getMissionListView().getMissionView().getMissionDepartmentListView().getMissionDepartmentView().getMissionStationListView().getMissionStationView().getMissionUnitListView().getMissionUnitView().show(breadcrumb, navigationPanel, mapElementsLayer, (MissionUnitLink) selectedObject);
            }
            else if (selectedObject instanceof MissionResponderLink) {
                utilsView.addBreadCrumb(breadcrumb, selectedObject);
                dispatchView.getMissionListView().getMissionView().getMissionDepartmentListView().getMissionDepartmentView().getMissionStationListView().getMissionStationView().getMissionResponderListView().getMissionResponderView().show(breadcrumb, navigationPanel, mapElementsLayer, (MissionResponderLink) selectedObject);
            }
            else if (selectedObject instanceof MissionVehicleLink) {
                utilsView.addBreadCrumb(breadcrumb, selectedObject);
                dispatchView.getMissionListView().getMissionView().getMissionDepartmentListView().getMissionDepartmentView().getMissionStationListView().getMissionStationView().getMissionVehicleListView().getMissionVehicleView().show(breadcrumb, navigationPanel, (MissionVehicleLink) selectedObject);
            }
        });
    }

    public void organizationButtonHandler(String buttonText, List<Department> departments) {
        viewRunnable = () -> organizationButtonHandler(buttonText, departments);
        utilsView.clearPane(navigationPanel);
        utilsView.clearPane(mapElementsLayer);
        utilsView.resetBreadCrumbBar(breadcrumb);
        utilsView.addBreadCrumb(breadcrumb, buttonText);
        organizationView.show(breadcrumb, navigationPanel, mapElementsLayer, departments);
    }

    public void dispatchButtonHandler(String buttonText, List<Mission> missions) {
        viewRunnable = () -> dispatchButtonHandler(buttonText, missions);
        utilsView.clearPane(navigationPanel);
        utilsView.clearPane(mapElementsLayer);
        utilsView.resetBreadCrumbBar(breadcrumb);
        utilsView.addBreadCrumb(breadcrumb, buttonText);
        dispatchView.show(breadcrumb, navigationPanel, mapElementsLayer, missions);
    }

    public void refreshUserInterface() {
        if (viewRunnable != null) {
            viewRunnable.run();
        }
    }

}
