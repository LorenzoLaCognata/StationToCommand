package stationtocommand.view.departmentStructure;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import stationtocommand.view.stationStructure.StationListView;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentView {

    private final UtilsView utilsView;
    private final StationListView stationListView;

    public DepartmentView(UtilsView utilsView) {
        this.utilsView = utilsView;
        this.stationListView = new StationListView(utilsView);
    }

    public StationListView getStationListView() {
        return stationListView;
    }

    public void show(View view, Department department) {
        View.viewRunnable = () -> show(view, department);
        utilsView.addBreadCrumb(view.getBreadCrumbBar(), department);
        view.getNavigationPanel().clearAll();
        view.getWorldMap().clear();
        showDepartmentDetails(view, department);
        showDepartmentButtons(view, department);
        showDepartmentStations(view, department);
    }

    private void showDepartmentDetails(View view, Department department) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, department.getDepartmentType());
        utilsView.addMainTitleLabel(horizontalTitlePane, department.toString());
    }

    private void showDepartmentButtons(View view, Department department) {
        Pane buttonsPane = view.getNavigationPanel().getButtonsPane();

        EventHandler<ActionEvent> stationsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentStations(view, department);
        };
        Button stationsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Stations", stationsButtonHandler);
        stationsButton.setGraphic(utilsView.smallIcon(StationType.FIRE_STATION));

        EventHandler<ActionEvent> unitsButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentUnits(view, department);
        };
        Button unitsButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Units", unitsButtonHandler);
        unitsButton.setGraphic(utilsView.smallIcon(FireUnitType.FIRE_ENGINE));

        EventHandler<ActionEvent> vehiclesButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentVehicles(view, department);
        };
        Button vehiclesButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Vehicles", vehiclesButtonHandler);
        vehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV));

        EventHandler<ActionEvent> respondersButtonHandler = event -> {
            utilsView.setPaneButtonsSelectionStyle(event, buttonsPane);
            showDepartmentResponders(view, department);
        };
        Button respondersButton = utilsView.addButtonToHorizontalPane(buttonsPane, "Responders", respondersButtonHandler);
        respondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01));

        utilsView.setButtonSelectedStyle(stationsButton);
    }

    private void showDepartmentStations(View view, Department department) {
        View.viewRunnable = () -> showDepartmentStations(view, department);

        view.getNavigationPanel().clearDetails();
        showDepartmentStationsSidebar(view, department);

        view.getWorldMap().clear();
        showDepartmentStationsMap(view, department);
    }

    private void showDepartmentStationsSidebar(View view, Department department) {
        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        view.getWorldMap().clear();
        for (Station station : department.getStations()) {
            stationListView.showStationSidebar(view, station);
        }
    }

    public void showDepartmentStationsMap(View view, Department department) {
        for (Station station : department.getStations()) {
            stationListView.getStationView().showStationUnitsMap(view, station);
        }
    }

    private void showDepartmentUnits(View view, Department department) {
        View.viewRunnable = () -> showDepartmentUnits(view, department);

        view.getNavigationPanel().clearDetails();
        showDepartmentUnitsSidebar(view, department);

        view.getWorldMap().clear();
        showDepartmentUnitsMap(view, department);
    }

    private void showDepartmentUnitsSidebar(View view, Department department) {
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

        List<Unit> units = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .toList();
        stationListView.getStationView().getUnitListView().show(view, units);
    }

    public void showDepartmentUnitsMap(View view, Department department) {
        for (Station station : department.getStations()) {
            stationListView.getStationView().showStationUnitsMap(view, station);
        }
    }

    private void showDepartmentVehicles(View view, Department department) {
        View.viewRunnable = () -> showDepartmentVehicles(view, department);

        view.getNavigationPanel().clearDetails();
        showDepartmentVehiclesSidebar(view, department);

        view.getWorldMap().clear();
        showDepartmentVehiclesMap(view, department);
    }

    private void showDepartmentVehiclesSidebar(View view, Department department) {
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

        List<Vehicle> vehicles = department.getStations().stream()
                .flatMap(station -> station.getVehicles().stream())
                .toList();
        stationListView.getStationView().getVehicleListView().show(view, vehicles);
    }

    public void showDepartmentVehiclesMap(View view, Department department) {
        for (Station station : department.getStations()) {
            stationListView.getStationView().showStationVehiclesMap(view, station);
        }
    }

    private void showDepartmentResponders(View view, Department department) {
        View.viewRunnable = () -> showDepartmentResponders(view, department);

        view.getNavigationPanel().clearDetails();
        showDepartmentRespondersSidebar(view, department);

        view.getWorldMap().clear();
        showDepartmentRespondersMap(view, department);

    }

    private void showDepartmentRespondersSidebar(View view, Department department) {
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

        List<Responder> responders = department.getStations().stream()
            .flatMap(station -> station.getResponders().stream())
            .toList();
        stationListView.getStationView().getResponderListView().show(view, responders);
    }

    public void showDepartmentRespondersMap(View view, Department department) {
        for (Station station : department.getStations()) {
            stationListView.getStationView().showStationRespondersMap(view, station);
        }
    }

}