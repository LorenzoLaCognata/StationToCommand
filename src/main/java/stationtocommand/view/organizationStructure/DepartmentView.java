package stationtocommand.view.organizationStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.AppearanceType;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationType;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitStatus;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.PoliceVehicleType;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleStatus;
import stationtocommand.model.vehicleStructure.VehicleType;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class DepartmentView {

    private final Department department;
    private final UtilsView utilsView;
    private final SortedMap<Station, StationView> stationViews;
    private final SortedMap<Unit, UnitView> unitViews;
    private final SortedMap<Vehicle, VehicleView> vehicleViews;
    private final SortedMap<Responder, ResponderView> responderViews;
    private final Group stationIcons;
    private final List<Group> unitIcons;
    private final List<Group> vehicleIcons;
    private final List<Group> responderIcons;


    public DepartmentView(Department department, View view, UtilsView utilsView) {
        this.department = department;
        this.utilsView = utilsView;
        this.stationIcons = new Group();
        this.stationViews = department.getStations().stream()
                                .map(station -> {
                                    StationView stationView = new StationView(station, view, utilsView);
                                    Node resourceIcon = utilsView.createResourceWithRandomLocationIcon(IconType.SMALL, IconColor.EMPTY, station.getStationType(), station.getLocation());
                                    stationIcons.getChildren().add(resourceIcon);
                                    return Map.entry(station, stationView);
                                })
                                .collect(Collectors.toMap(
                                        Map.Entry::getKey,
                                        Map.Entry::getValue,
                                        (_, b) -> b,
                                        TreeMap::new
                                ));
        this.unitIcons = this.stationViews.values().stream()
                .map(StationView::getUnitIcons)
                .collect(Collectors.toList());
        this.unitViews =  this.stationViews.values().stream()
                .flatMap(stationView -> stationView.getUnitViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (_, b) -> b,
                        TreeMap::new
                ));
        this.vehicleIcons = this.unitViews.values().stream()
                .map(UnitView::getVehicleIcons)
                .collect(Collectors.toList());
        this.vehicleViews =  this.unitViews.values().stream()
                .flatMap(unitView -> unitView.getVehicleViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (_, b) -> b,
                        TreeMap::new
                ));
        this.responderIcons = this.unitViews.values().stream()
                .map(UnitView::getResponderIcons)
                .collect(Collectors.toList());
        this.responderViews =  this.unitViews.values().stream()
                .flatMap(unitView -> unitView.getResponderViews().entrySet().stream())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (_, b) -> b,
                        TreeMap::new
                ));
    }

    public Department getDepartment() {
        return department;
    }

    public StationView getStationView(Station station) {
        return stationViews.get(station);
    }

    public void addOrganizationDetailsDepartment(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addDepartmentIcon(horizontalDetailsPane);
        addDepartmentButton(view, horizontalDetailsPane);
    }

    private void addDepartmentTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, department.getDepartmentType());
        utilsView.addMainTitleLabel(horizontalTitlePane, department.toString());
    }

    private void addDepartmentIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, department.getDepartmentType());
    }

    private void addDepartmentButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, department.toString(), (_ -> showDepartment(view)));
    }

    public void showDepartment(View view) {
        View.viewRunnable = () -> showDepartment(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), department);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().clear();
        addDepartmentTitle(view);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> stationsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentStations(view);
        };
        Button stationsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Stations", stationsButtonHandler);
        stationsButton.setGraphic(utilsView.smallIcon(StationType.FIRE_STATION.getResourcePath(), ""));

        EventHandler<ActionEvent> unitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentUnits(view);
        };
        Button unitsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Units", unitsButtonHandler);
        unitsButton.setGraphic(utilsView.smallIcon(FireUnitType.FIRE_ENGINE.getResourcePath(), ""));

        EventHandler<ActionEvent> vehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentVehicles(view);
        };
        Button vehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", vehiclesButtonHandler);
        vehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV.getResourcePath(), ""));

        EventHandler<ActionEvent> respondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentResponders(view);
        };
        Button respondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", respondersButtonHandler);
        respondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(stationsButton);
        showDepartmentStations(view);
    }

    private void showDepartmentStations(View view) {
        View.viewRunnable = () -> showDepartmentStations(view);
        showDepartmentStationsDetails(view);
        showDepartmentStationsMap(view);
    }

    private void showDepartmentStationsDetails(View view) {
        view.getNavigationPanel().clearDetails();
        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        for (Station station : department.getStations()) {
            getStationView(station).addDepartmentDetailsStation(view);
        }
    }

    public void showDepartmentStationsMap(View view) {
        view.getWorldMap().clear();
        for (Group unitIconsGroup : unitIcons) {
            unitIconsGroup.setVisible(false);
        }
        for (Group vehicleIconsGroup : vehicleIcons) {
            vehicleIconsGroup.setVisible(false);
        }
        for (Group responderIconsGroup : responderIcons) {
            responderIconsGroup.setVisible(false);
        }
        Pane mapLayer = view.getWorldMap().getMapElementsLayer();
        stationIcons.setVisible(true);
        if (!mapLayer.getChildren().contains(stationIcons)) {
            mapLayer.getChildren().add(stationIcons);
        }
    }

    private void showDepartmentUnits(View view) {
        View.viewRunnable = () -> showDepartmentUnits(view);
        showDepartmentUnitsDetails(view);
        showDepartmentUnitsMap(view);
    }

    private void showDepartmentUnitsDetails(View view) {
        view.getNavigationPanel().clearDetails();

        Map<UnitStatus, Long> unitStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .collect(Collectors.groupingBy(
                        Unit::getUnitStatus,
                        Collectors.counting())
                );

        Map<UnitType, Map<UnitStatus, Long>> unitTypeStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .collect(Collectors.groupingBy(
                                Unit::getUnitType,
                                Collectors.groupingBy(
                                        Unit::getUnitStatus,
                                        Collectors.counting())
                        )
                );

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        utilsView.addAvailabilityCount(horizontalDetailsPane, unitStatusCounts, unitTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        for (UnitView unitView : unitViews.values()) {
            unitView.addStationDetailsUnit(view);
        }
    }

    public void showDepartmentUnitsMap(View view) {
        view.getWorldMap().clear();
        stationIcons.setVisible(false);
        for (Group vehicleIconsGroup : vehicleIcons) {
            vehicleIconsGroup.setVisible(false);
        }
        for (Group responderIconsGroup : responderIcons) {
            responderIconsGroup.setVisible(false);
        }
        Pane mapLayer = view.getWorldMap().getMapElementsLayer();
        for (Group unitIconsGroup : unitIcons) {
            unitIconsGroup.setVisible(true);
            if (!mapLayer.getChildren().contains(unitIconsGroup)) {
                mapLayer.getChildren().add(unitIconsGroup);
            }
        }
    }

    private void showDepartmentVehicles(View view) {
        View.viewRunnable = () -> showDepartmentVehicles(view);
        showDepartmentVehiclesDetails(view);
        showDepartmentVehiclesMap(view);
    }

    private void showDepartmentVehiclesDetails(View view) {
        view.getNavigationPanel().clearDetails();

        Map<VehicleStatus, Long> vehicleStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getVehicles().stream())
                .collect(Collectors.groupingBy(
                        Vehicle::getVehicleStatus,
                        Collectors.counting())
                );

        Map<VehicleType, Map<VehicleStatus, Long>> vehicleTypeStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getVehicles().stream())
                .collect(Collectors.groupingBy(
                                Vehicle::getVehicleType,
                                Collectors.groupingBy(
                                        Vehicle::getVehicleStatus,
                                        Collectors.counting())
                        )
                );

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        utilsView.addAvailabilityCount(horizontalDetailsPane, vehicleStatusCounts, vehicleTypeStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        for (VehicleView vehicleView : vehicleViews.values()) {
            vehicleView.addStationDetailsVehicle(view);
        }
    }

    public void showDepartmentVehiclesMap(View view) {
        view.getWorldMap().clear();
        stationIcons.setVisible(false);
        for (Group unitIconsGroup : unitIcons) {
            unitIconsGroup.setVisible(false);
        }
        for (Group responderIconsGroup : responderIcons) {
            responderIconsGroup.setVisible(false);
        }
        Pane mapLayer = view.getWorldMap().getMapElementsLayer();
        for (Group vehicleIconsGroup : vehicleIcons) {
            vehicleIconsGroup.setVisible(true);
            if (!mapLayer.getChildren().contains(vehicleIconsGroup)) {
                mapLayer.getChildren().add(vehicleIconsGroup);
            }
        }
    }

    private void showDepartmentResponders(View view) {
        View.viewRunnable = () -> showDepartmentResponders(view);
        showDepartmentRespondersDetails(view);
        showDepartmentRespondersMap(view);
    }

    private void showDepartmentRespondersDetails(View view) {
        view.getNavigationPanel().clearDetails();

        Map<ResponderStatus, Long> responderStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .collect(Collectors.groupingBy(
                    Responder::getResponderStatus,
                    Collectors.counting())
                );

        Map<RankType, Map<ResponderStatus, Long>> responderRankStatusCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .collect(Collectors.groupingBy(
                    responder -> responder.getRank().getRankType(),
                    Collectors.groupingBy(
                        Responder::getResponderStatus,
                        Collectors.counting())
                    )
                );

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        utilsView.addAvailabilityCount(horizontalDetailsPane, responderStatusCounts, responderRankStatusCounts);

        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        for (ResponderView responderView : responderViews.values()) {
            responderView.addStationDetailsResponder(view);
        }
    }

    public void showDepartmentRespondersMap(View view) {
        view.getWorldMap().clear();
        stationIcons.setVisible(false);
        for (Group unitIconsGroup : unitIcons) {
            unitIconsGroup.setVisible(false);
        }
        for (Group vehicleIconsGroup : vehicleIcons) {
            vehicleIconsGroup.setVisible(false);
        }
        Pane mapLayer = view.getWorldMap().getMapElementsLayer();
        for (Group responderIconsGroup : responderIcons) {
            responderIconsGroup.setVisible(true);
            if (!mapLayer.getChildren().contains(responderIconsGroup)) {
                mapLayer.getChildren().add(responderIconsGroup);
            }
        }
    }

}