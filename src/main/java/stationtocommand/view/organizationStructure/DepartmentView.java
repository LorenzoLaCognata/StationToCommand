package stationtocommand.view.organizationStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.personStructure.AppearanceType;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationType;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitStatus;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.*;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.*;
import java.util.stream.Collectors;

public class DepartmentView {

    private final Department department;
    private final UtilsView utilsView;
    private final SortedMap<Station, StationView> stationViews;
    private final SortedMap<Unit, UnitView> unitViews;
    private final SortedMap<Vehicle, VehicleView> vehicleViews;
    private final SortedMap<Responder, ResponderView> responderViews;

    public DepartmentView(Department department, View view, UtilsView utilsView) {
        this.department = department;
        this.utilsView = utilsView;

        this.stationViews = new TreeMap<>();
        for (Station station : department.getStations()) {
            StationView stationView = new StationView(station, view, utilsView);
            stationViews.put(station, stationView);
            view.getWorldMap().addMapElement(stationView.getNode());
        }

        this.unitViews = new TreeMap<>();
        this.vehicleViews = new TreeMap<>();
        this.responderViews = new TreeMap<>();
        for (StationView stationView : stationViews.values()) {
            for (UnitView unitView : stationView.getUnitViews().values()) {
                unitViews.put(unitView.getUnit(), unitView);
                view.getWorldMap().addMapElement(unitView.getNode());
            }
            for (VehicleView vehicleView : stationView.getVehicleViews().values()) {
                vehicleViews.put(vehicleView.getVehicle(), vehicleView);
                view.getWorldMap().addMapElement(vehicleView.getNode());
            }
            for (ResponderView responderView : stationView.getResponderViews().values()) {
                responderViews.put(responderView.getResponder(), responderView);
                view.getWorldMap().addMapElement(responderView.getNode());
            }
        }

    }

    public Department getDepartment() {
        return department;
    }

    public SortedMap<Station, StationView> getStationViews() {
        return stationViews;
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
        view.getWorldMap().setMapElementsNotVisible();
        addDepartmentTitle(view);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> stationsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentStations(view);
        };
        Button stationsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Stations", stationsButtonHandler);
        StationType stationType = switch(department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> StationType.FIRE_STATION;
            case POLICE_DEPARTMENT -> StationType.POLICE_STATION;
            case MEDIC_DEPARTMENT -> StationType.MEDIC_STATION;
        };
        stationsButton.setGraphic(utilsView.smallIcon(stationType.getResourcePath(), ""));

        EventHandler<ActionEvent> unitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentUnits(view);
        };
        Button unitsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Units", unitsButtonHandler);
        UnitType unitType = switch(department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireUnitType.values()[0];
            case POLICE_DEPARTMENT -> PoliceUnitType.values()[0];
            case MEDIC_DEPARTMENT -> MedicUnitType.values()[0];
        };
        unitsButton.setGraphic(utilsView.smallIcon(unitType.getResourcePath(), ""));

        EventHandler<ActionEvent> vehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentVehicles(view);
        };
        Button vehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", vehiclesButtonHandler);
        VehicleType vehicleType = switch(department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> FireVehicleType.values()[0];
            case POLICE_DEPARTMENT -> PoliceVehicleType.values()[0];
            case MEDIC_DEPARTMENT -> MedicVehicleType.values()[0];
        };
        vehiclesButton.setGraphic(utilsView.smallIcon(vehicleType.getResourcePath(), ""));

        EventHandler<ActionEvent> respondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentResponders(view);
        };
        Button respondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", respondersButtonHandler);
        Responder chiefResponder = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .min(Comparator.naturalOrder())
                .orElse(null);
        respondersButton.setGraphic(utilsView.smallIcon(chiefResponder != null ? chiefResponder.getAppearanceType().getResourcePath() : AppearanceType.MALE_01.getResourcePath(), ""));

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
        for (StationView stationView : stationViews.values()) {
            stationView.addDepartmentDetailsStation(view);
        }
    }

    public void showDepartmentStationsMap(View view) {
        view.getWorldMap().setMapElementsNotVisible();

        Map<Location, List<StationView>> stationViewsByLocation = stationViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getStation().getLocation()
                ));

        for (Map.Entry<Location, List<StationView>> locationStationViews : stationViewsByLocation.entrySet()) {
            Point2D nodesCenter = utilsView.locationToPoint(locationStationViews.getKey(), IconType.SMALL);
            List<Node> locationNodes = locationStationViews.getValue().stream()
                    .map(StationView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(nodesCenter, locationNodes);
        }

        for (StationView stationView : stationViews.values()) {
            stationView.setNodeVisible();
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
        view.getWorldMap().setMapElementsNotVisible();

        Map<Location, List<UnitView>> unitViewsByLocation = unitViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getUnit().getStation().getLocation()
                ));

        for (Map.Entry<Location, List<UnitView>> locationUnitViews : unitViewsByLocation.entrySet()) {
            Point2D nodesCenter = utilsView.locationToPoint(locationUnitViews.getKey(), IconType.SMALL);
            List<Node> locationNodes = locationUnitViews.getValue().stream()
                    .map(UnitView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(nodesCenter, locationNodes);
        }

        for (UnitView unitView : unitViews.values()) {
            unitView.setNodeVisible();
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
        view.getWorldMap().setMapElementsNotVisible();

        Map<Location, List<VehicleView>> vehicleViewsByLocation = vehicleViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getVehicle().getLocation()
                ));

        for (Map.Entry<Location, List<VehicleView>> locationVehicleViews : vehicleViewsByLocation.entrySet()) {
            Point2D nodesCenter = utilsView.locationToPoint(locationVehicleViews.getKey(), IconType.SMALL);
            List<Node> locationNodes = locationVehicleViews.getValue().stream()
                    .map(VehicleView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(nodesCenter, locationNodes);
        }

        for (VehicleView vehicleView : vehicleViews.values()) {
            vehicleView.setNodeVisible();
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
        view.getWorldMap().setMapElementsNotVisible();

        Map<Location, List<ResponderView>> responderViewsByLocation = responderViews.values().stream()
                .collect(Collectors.groupingBy(
                        v -> v.getResponder().getLocation()
                ));

        for (Map.Entry<Location, List<ResponderView>> locationResponderViews : responderViewsByLocation.entrySet()) {
            Point2D nodesCenter = utilsView.locationToPoint(locationResponderViews.getKey(), IconType.SMALL);
            List<Node> locationNodes = locationResponderViews.getValue().stream()
                    .map(ResponderView::getNode)
                    .toList();
            utilsView.distributeResourceIconsByLocation(nodesCenter, locationNodes);
        }

        for (ResponderView responderView : responderViews.values()) {
            responderView.setNodeVisible();
        }
    }

}