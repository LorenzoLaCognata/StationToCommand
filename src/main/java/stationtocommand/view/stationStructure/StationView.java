package stationtocommand.view.stationStructure;

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
import stationtocommand.view.responderStructure.ResponderListView;
import stationtocommand.view.unitStructure.UnitView;
import stationtocommand.view.vehicleStructure.VehicleListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StationView {

    private final UtilsView utilsView;
    private final UnitView unitView;
    private final ResponderListView responderListView;
    private final VehicleListView vehicleListView;

    public StationView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.unitView = new UnitView(utilsView);
        this.responderListView = new ResponderListView(utilsView);
        this.vehicleListView = new VehicleListView(utilsView);
    }

    public UnitView getUnitView() {
        return unitView;
    }

    public ResponderListView getResponderListView() {
        return responderListView;
    }

    public VehicleListView getVehicleListView() {
        return vehicleListView;
    }

    public void departmentDetailsStationEntry(View view, Station station) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        stationIcon(horizontalDetailsPane, station);
        stationButton(view, horizontalDetailsPane, station);
        stationUnitTypesIcons(horizontalDetailsPane, station);
    }

    private void stationIcon(Pane pane, Station station) {
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, station.getStationType());
    }

    private void stationButton(View view, Pane pane, Station station) {
        utilsView.addButtonToPane(pane, station.toString(), (_ -> show(view, station)));
    }

    private void stationUnitTypesIcons(Pane pane, Station station) {
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

    public void show(View view, Station station) {
        View.viewRunnable = () -> show(view, station);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), station);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().clear();
        stationTitle(view, station);
        stationButtons(view, station);
        stationUnitsView(view, station);
    }

    private void stationTitle(View view, Station station) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, station.getStationType());
        utilsView.addMainTitleLabel(horizontalTitlePane, station.toString());
    }

    private void stationButtons(View view, Station station) {
        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> unitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            stationUnitsView(view, station);
        };
        Button unitsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Units", unitsButtonHandler);
        unitsButton.setGraphic(utilsView.smallIcon(FireUnitType.FIRE_ENGINE));

        EventHandler<ActionEvent> vehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            stationVehiclesView(view, station);
        };
        Button vehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", vehiclesButtonHandler);
        vehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV));

        EventHandler<ActionEvent> respondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            stationRespondersView(view, station);
        };
        Button respondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", respondersButtonHandler);
        respondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01));

        utilsView.setButtonSelectedStyle(unitsButton);
    }

    private void stationUnitsView(View view, Station station) {
        View.viewRunnable = () -> stationUnitsView(view, station);

        view.getNavigationPanel().clearDetails();
        stationUnitsViewDetails(view, station);

        view.getWorldMap().clear();
        stationUnitsViewMap(view, station);
    }

    public void stationUnitsViewDetails(View view, Station station) {
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
        for (Unit unit : station.getUnits()) {
            unitView.stationDetailsUnitEntry(view, unit);
        }

    }

    public void stationUnitsViewMap(View view, Station station) {
        Point2D point = utilsView.locationToPoint(station.getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallIcon(station.getStationType());
        utilsView.addNodeToPane(view.getWorldMap().getMapElementsLayer(), imageView, point);
        for (Unit unit : station.getUnits()) {
            unitView.showUnitVehiclesMap(view, unit);
        }
    }

    private void stationVehiclesView(View view, Station station) {
        View.viewRunnable = () -> stationVehiclesView(view, station);

        view.getNavigationPanel().clearDetails();
        stationVehiclesViewDetails(view, station);

        view.getWorldMap().clear();
        stationVehiclesViewMap(view, station);
    }

    public void stationVehiclesViewDetails(View view, Station station) {
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
        vehicleListView.show(view, station.getVehicles());
    }

    public void stationVehiclesViewMap(View view, Station station) {
        for (Vehicle vehicle : station.getVehicles()) {
            Point2D point = utilsView.locationToPoint(vehicle.getLocation(), IconType.SMALL);
            ImageView imageView = utilsView.smallIcon(vehicle.getVehicleType());
            utilsView.addNodeToPane(view.getWorldMap().getMapElementsLayer(), imageView, point);
        }
    }

    private void stationRespondersView(View view, Station station) {
        View.viewRunnable = () -> stationRespondersView(view, station);

        view.getNavigationPanel().clearDetails();
        stationRespondersViewDetails(view, station);

        view.getWorldMap().clear();
        stationRespondersViewMap(view, station);
    }

    private void stationRespondersViewDetails(View view, Station station) {
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
        responderListView.show(view, station.getResponders());
    }

    public void stationRespondersViewMap(View view, Station station) {
        for (Responder responder : station.getResponders()) {
            Point2D point = utilsView.locationToPoint(responder.getLocation(), IconType.SMALL);
            ImageView imageView = utilsView.smallIcon(responder.getAppearanceType());
            utilsView.addNodeToPane(view.getWorldMap().getMapElementsLayer(), imageView, point);
        }
    }

}