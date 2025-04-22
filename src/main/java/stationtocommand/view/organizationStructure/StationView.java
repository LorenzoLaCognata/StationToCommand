package stationtocommand.view.organizationStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.AppearanceType;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitStatus;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.PoliceVehicleType;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleStatus;
import stationtocommand.model.vehicleStructure.VehicleType;
import stationtocommand.view.View;
import stationtocommand.view.mainStructure.IconColor;
import stationtocommand.view.mainStructure.IconType;
import stationtocommand.view.mainStructure.UtilsView;

import java.util.*;
import java.util.stream.Collectors;

public class StationView {

    private final Station station;
    private final Node node;
    private final UtilsView utilsView;
    private final SortedMap<Unit, UnitView> unitViews;
    private final SortedMap<Vehicle, VehicleView> vehicleViews;
    private final SortedMap<Responder, ResponderView> responderViews;

    public StationView(Station station, View view, UtilsView utilsView) {
        this.station = station;
        this.node = utilsView.createResourceIconWithLocation(IconType.SMALL, IconColor.EMPTY, station.getStationType(), station.getLocation());
        this.utilsView = utilsView;

        this.unitViews = new TreeMap<>();
        for (Unit unit : station.getUnits()) {
            UnitView unitView = new UnitView(unit, view, utilsView);
            unitViews.put(unit, unitView);
            view.getWorldMap().addMapElement(unitView.getNode());
        }

        this.vehicleViews = new TreeMap<>();
        this.responderViews = new TreeMap<>();
        for (UnitView unitView : unitViews.values()) {
            for (VehicleView vehicleView : unitView.getVehicleViews().values()) {
                vehicleViews.put(vehicleView.getVehicle(), vehicleView);
                view.getWorldMap().addMapElement(vehicleView.getNode());
            }
            for (ResponderView responderView : unitView.getResponderViews().values()) {
                responderViews.put(responderView.getResponder(), responderView);
                view.getWorldMap().addMapElement(responderView.getNode());
            }
        }
    }

    public Station getStation() {
        return station;
    }

    public Node getNode() {
        return node;
    }

    public SortedMap<Unit, UnitView> getUnitViews() {
        return unitViews;
    }

    public UnitView getUnitView(Unit unit) {
        return unitViews.get(unit);
    }

    public SortedMap<Vehicle, VehicleView> getVehicleViews() {
        return vehicleViews;
    }

    public VehicleView getVehicleView(Vehicle vehicle) {
        return vehicleViews.get(vehicle);
    }

    public SortedMap<Responder, ResponderView> getResponderViews() {
        return responderViews;
    }

    public ResponderView getResponderView(Responder responder) {
        return responderViews.get(responder);
    }

    public void addDepartmentDetailsStation(View view) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        addStationIcon(horizontalDetailsPane);
        addStationButton(view, horizontalDetailsPane);
        addStationUnitTypesIcons(horizontalDetailsPane);
    }

    private void addStationIcon(Pane pane) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, station.getStationType());
    }

    private void addStationButton(View view, Pane pane) {
        utilsView.addButtonToPane(pane, station.toString(), (_ -> showStation(view)));
    }

    private void addStationUnitTypesIcons(Pane pane) {
        List<UnitType> unitTypes;

        switch (station.getDepartment().getDepartmentType()) {
            case DepartmentType.FIRE_DEPARTMENT -> unitTypes = List.of(FireUnitType.values());
            case DepartmentType.POLICE_DEPARTMENT -> unitTypes = List.of(PoliceUnitType.values());
            case DepartmentType.MEDIC_DEPARTMENT -> unitTypes = List.of(MedicUnitType.values());
            default -> unitTypes = new ArrayList<>();
        }

        for (UnitType unitType : unitTypes) {
            if (station.getUnitManager().getUnits(unitType).isEmpty()) {
                utilsView.addIconToPane(pane, IconType.SMALL_FADED, IconColor.EMPTY, unitType);

            }
            else {
                utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, unitType);

            }
        }
    }

    public void showStation(View view) {
        View.viewRunnable = () -> showStation(view);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), station);
        view.getNavigationPanel().clearAll();
        showStationDetails(view);
    }

    private void showStationDetails(View view) {
        addStationTitle(view);

        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> unitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showStationUnits(view);
        };
        Button unitsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Units", unitsButtonHandler);
        unitsButton.setGraphic(utilsView.smallIcon(FireUnitType.FIRE_ENGINE.getResourcePath(), ""));

        EventHandler<ActionEvent> vehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showStationVehicles(view);
        };
        Button vehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", vehiclesButtonHandler);
        vehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV.getResourcePath(), ""));

        EventHandler<ActionEvent> respondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showStationResponders(view);
        };
        Button respondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", respondersButtonHandler);
        respondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01.getResourcePath(), ""));

        utilsView.setButtonSelectedStyle(unitsButton);
        showStationUnits(view);
    }

    private void addStationTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, station.getStationType());
        utilsView.addMainTitleLabel(horizontalTitlePane, station.toString());
    }

    public void setNodeVisible() {
        node.setVisible(true);
    }

    private void showStationUnits(View view) {
        View.viewRunnable = () -> showStationUnits(view);
        showStationUnitsDetails(view);
        showStationUnitsMap(view);
    }

    private void showStationUnitsDetails(View view) {
        view.getNavigationPanel().clearDetails();

        Map<UnitStatus, Long> unitStatusCounts = station.getUnits().stream()
            .collect(Collectors.groupingBy(
                Unit::getUnitStatus,
                Collectors.counting())
            );

        Map<UnitType, Map<UnitStatus, Long>> unitTypeStatusCounts = station.getUnits().stream()
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

    public void showStationUnitsMap(View view) {
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

    private void showStationVehicles(View view) {
        View.viewRunnable = () -> showStationVehicles(view);
        showStationVehiclesDetails(view);
        showStationVehiclesMap(view);
    }

    private void showStationVehiclesDetails(View view) {
        view.getNavigationPanel().clearDetails();

        Map<VehicleStatus, Long> vehicleStatusCounts = station.getUnits().stream()
            .flatMap(unit -> unit.getVehicles().stream())
            .collect(Collectors.groupingBy(
                Vehicle::getVehicleStatus,
                Collectors.counting())
            );

        Map<VehicleType, Map<VehicleStatus, Long>> vehicleTypeStatusCounts = station.getUnits().stream()
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

    public void showStationVehiclesMap(View view) {
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

    private void showStationResponders(View view) {
        View.viewRunnable = () -> showStationResponders(view);
        showStationRespondersDetails(view);
        showStationRespondersMap(view);
    }

    private void showStationRespondersDetails(View view) {
        view.getNavigationPanel().clearDetails();

        Map<ResponderStatus, Long> responderStatusCounts = station.getUnits().stream()
            .flatMap(unit -> unit.getResponders().stream())
            .collect(Collectors.groupingBy(
                Responder::getResponderStatus,
                Collectors.counting())
            );

        Map<RankType, Map<ResponderStatus, Long>> responderRankStatusCounts = station.getUnits().stream()
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

    public void showStationRespondersMap(View view) {
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