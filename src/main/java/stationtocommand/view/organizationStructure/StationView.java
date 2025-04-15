package stationtocommand.view.organizationStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.AppearanceType;
import stationtocommand.model.departmentStructure.DepartmentType;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StationView {

    private final Station station;
    private final Map<Unit, UnitView> unitViews;
    private final Map<Vehicle, VehicleView> vehicleViews;
    private final Map<Responder, ResponderView> responderViews;
    private final UtilsView utilsView;

    public StationView(Station station, UtilsView utilsView) {
        this.station = station;
        this.unitViews = station.getUnits().stream()
                .collect(Collectors.toMap(
                        unit -> unit, unit -> new UnitView(unit, utilsView))
                );
        this.vehicleViews = station.getVehicles().stream()
                .collect(Collectors.toMap(
                        vehicle -> vehicle, vehicle -> new VehicleView(vehicle, utilsView))
                );
        this.responderViews = station.getResponders().stream()
                .collect(Collectors.toMap(
                        responder -> responder, responder -> new ResponderView(responder, utilsView))
                );
        this.utilsView = utilsView;
    }

    public List<UnitView> getUnitViews() {
        return unitViews.values().stream().toList();
    }

    public UnitView getUnitView(Unit unit) {
        return unitViews.get(unit);
    }

    public List<VehicleView> getVehicleViews() {
        return vehicleViews.values().stream().toList();
    }

    public VehicleView getVehicleView(Vehicle vehicle) {
        return vehicleViews.get(vehicle);
    }

    public List<ResponderView> getResponderViews() {
        return responderViews.values().stream().toList();
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
        view.getWorldMap().clear();
        showStationDetails(view);
        showStationMap(view);
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

    private void showStationUnits(View view) {
        View.viewRunnable = () -> showStationUnits(view);
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

    private void showStationVehicles(View view) {
        View.viewRunnable = () -> showStationVehicles(view);
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

    private void showStationResponders(View view) {
        View.viewRunnable = () -> showStationResponders(view);
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

    public void showStationMap(View view) {
        Point2D point = utilsView.locationToPoint(station.getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallShadowIcon(station.getStationType().getResourcePath(), station.getStationType().toString(), utilsView.departmentIconColor(station.getDepartment()));
        Pane mapLayer = view.getWorldMap().getMapElementsLayer();
        utilsView.addNodeToPane(mapLayer, imageView, point);
    }

}