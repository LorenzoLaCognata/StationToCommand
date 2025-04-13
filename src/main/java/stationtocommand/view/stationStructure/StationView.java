package stationtocommand.view.stationStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import stationtocommand.model.departmentStructure.AppearanceType;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.stationStructure.Station;
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
import stationtocommand.view.responderStructure.ResponderListView;
import stationtocommand.view.unitStructure.UnitListView;
import stationtocommand.view.vehicleStructure.VehicleListView;

import java.util.Map;
import java.util.stream.Collectors;

public class StationView {

    private boolean isSelected = false;

    private final UtilsView utilsView;
    private final UnitListView unitListView;
    private final ResponderListView responderListView;
    private final VehicleListView vehicleListView;

    public StationView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.unitListView = new UnitListView(utilsView);
        this.responderListView = new ResponderListView(utilsView);
        this.vehicleListView = new VehicleListView(utilsView);
    }

    public UnitListView getUnitListView() {
        return unitListView;
    }

    public ResponderListView getResponderListView() {
        return responderListView;
    }

    public VehicleListView getVehicleListView() {
        return vehicleListView;
    }

    public void show(View view, Station station) {
        View.viewRunnable = () -> show(view, station);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), station);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().clear();
        showStationDetails(view, station);
        showStationButtons(view, station);
        showStationUnits(view, station);
    }

    private void showStationDetails(View view, Station station) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, station.getStationType());
        utilsView.addMainTitleLabel(horizontalTitlePane, station.toString());
    }

    private void showStationButtons(View view, Station station) {
        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> unitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showStationUnits(view, station);
        };
        Button unitsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Units", unitsButtonHandler);
        unitsButton.setGraphic(utilsView.smallIcon(FireUnitType.FIRE_ENGINE));

        EventHandler<ActionEvent> vehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showStationVehicles(view, station);
        };
        Button vehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", vehiclesButtonHandler);
        vehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV));

        EventHandler<ActionEvent> respondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showStationResponders(view, station);
        };
        Button respondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", respondersButtonHandler);
        respondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01));

        utilsView.setButtonSelectedStyle(unitsButton);
    }

    private void showStationUnits(View view, Station station) {
        View.viewRunnable = () -> showStationUnits(view, station);

        view.getNavigationPanel().clearDetails();
        showStationUnitsSidebar(view, station);

        view.getWorldMap().clear();
        showStationUnitsMap(view, station);
    }

    public void showStationUnitsSidebar(View view, Station station) {
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
        unitListView.show(view, station.getUnits());
    }

    public void showStationUnitsMap(View view, Station station) {
        Point2D point = utilsView.locationToPoint(station.getLocation(), IconType.SMALL);
        ImageView imageView = utilsView.smallIcon(station.getStationType());
        utilsView.addNodeToPane(view.getWorldMap().getMapElementsLayer(), imageView, point);
    }

    private void showStationVehicles(View view, Station station) {
        View.viewRunnable = () -> showStationVehicles(view, station);

        view.getNavigationPanel().clearDetails();
        showStationVehiclesSidebar(view, station);

        view.getWorldMap().clear();
        showStationVehiclesMap(view, station);
    }

    public void showStationVehiclesSidebar(View view, Station station) {
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

    public void showStationVehiclesMap(View view, Station station) {
        for (Vehicle vehicle : station.getVehicles()) {
            Point2D point = utilsView.locationToPoint(vehicle.getLocation(), IconType.SMALL);
            ImageView imageView = utilsView.smallIcon(vehicle.getVehicleType());
            utilsView.addNodeToPane(view.getWorldMap().getMapElementsLayer(), imageView, point);
        }
    }

    private void showStationResponders(View view, Station station) {
        View.viewRunnable = () -> showStationResponders(view, station);

        view.getNavigationPanel().clearDetails();
        showStationRespondersSidebar(view, station);

        view.getWorldMap().clear();
        showStationRespondersMap(view, station);
    }

    private void showStationRespondersSidebar(View view, Station station) {
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

    public void showStationRespondersMap(View view, Station station) {
        for (Responder responder : station.getResponders()) {
            Point2D point = utilsView.locationToPoint(responder.getLocation(), IconType.SMALL);
            ImageView imageView = utilsView.smallIcon(responder.getAppearanceType());
            utilsView.addNodeToPane(view.getWorldMap().getMapElementsLayer(), imageView, point);
        }
    }

}