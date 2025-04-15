package stationtocommand.view.organizationStructure;

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

import java.util.Map;
import java.util.stream.Collectors;

public class DepartmentView {

    private final Department department;
    private final Map<Station, StationView> stationViews;
    private final UtilsView utilsView;

    public DepartmentView(Department department, View view, UtilsView utilsView) {
        this.department = department;
        this.stationViews = department.getStations().stream()
                                .collect(Collectors.toMap(
                                        station -> station, station -> new StationView(station, view, utilsView))
                                );
        this.utilsView = utilsView;
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

    private void addDepartmentTitle(View view) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, department.getDepartmentType());
        utilsView.addMainTitleLabel(horizontalTitlePane, department.toString());
    }

    private void showDepartmentStations(View view) {
        View.viewRunnable = () -> showDepartmentStations(view);
        view.getNavigationPanel().clearDetails();
        view.getWorldMap().clear();
        utilsView.addSeparatorToPane(view.getNavigationPanel().getDetailsPane());
        for (Station station : department.getStations()) {
            getStationView(station).addDepartmentDetailsStation(view);
            getStationView(station).showStationMap(view);
        }
    }

    private void showDepartmentUnits(View view) {
        View.viewRunnable = () -> showDepartmentUnits(view);
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

        for (StationView stationView : stationViews.values()) {
            for (UnitView unitView : stationView.getUnitViews()) {
                unitView.addStationDetailsUnit(view);
            }
        }
    }

    private void showDepartmentVehicles(View view) {
        View.viewRunnable = () -> showDepartmentVehicles(view);
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

        for (StationView stationView : stationViews.values()) {
            for (VehicleView vehicleView : stationView.getVehicleViews()) {
                vehicleView.addStationDetailsVehicle(view);
            }
        }

    }

    private void showDepartmentResponders(View view) {
        View.viewRunnable = () -> showDepartmentResponders(view);
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

        for (StationView stationView : stationViews.values()) {
            for (ResponderView responderView : stationView.getResponderViews()) {
                responderView.addStationDetailsResponder(view);
            }
        }
    }

    public void showMap(View view) {
        for (Station station : department.getStations()) {
            getStationView(station).showStationMap(view);
        }
    }

}