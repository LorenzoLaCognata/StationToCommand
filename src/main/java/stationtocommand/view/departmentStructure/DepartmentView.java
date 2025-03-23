package stationtocommand.view.departmentStructure;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.AppearanceType;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.rankTypeStructure.FireRankType;
import stationtocommand.model.rankTypeStructure.MedicRankType;
import stationtocommand.model.rankTypeStructure.PoliceRankType;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
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
import stationtocommand.view.stationStructure.StationListView;

import java.util.Collections;
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

    public void show(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane worldMap, Department department) {
        View.viewRunnable = () -> show(breadCrumbBar, navigationPanel, worldMap, department);
        utilsView.addBreadCrumb(breadCrumbBar, department);
        utilsView.clearPane(navigationPanel);
        utilsView.clearPane(worldMap);
        showDepartmentDetails(navigationPanel, department);

        Pane choicePane = utilsView.createHBox(navigationPanel);
        Pane detailsPane = utilsView.createVBox(navigationPanel);

        Button stationsButton = utilsView.addButtonToHorizontalPane(choicePane, "Stations", (_ -> showDepartmentStations(breadCrumbBar, navigationPanel, detailsPane, worldMap, department)));
        stationsButton.setGraphic(utilsView.smallIcon(StationType.FIRE_STATION.getResourcePath(), ""));
        Button unitsButton = utilsView.addButtonToHorizontalPane(choicePane, "Units", (_ -> showDepartmentUnitCounts(detailsPane, department)));
        unitsButton.setGraphic(utilsView.smallIcon(FireUnitType.FIRE_ENGINE.getResourcePath(), ""));
        Button vehiclesButton = utilsView.addButtonToHorizontalPane(choicePane, "Vehicles", (_ -> showDepartmentVehicleCounts(detailsPane, department)));
        vehiclesButton.setGraphic(utilsView.smallIcon(PoliceVehicleType.SUV.getResourcePath(), ""));
        Button respondersButton = utilsView.addButtonToHorizontalPane(choicePane, "Responders", (_ -> showDepartmentResponderCounts(detailsPane, department)));
        respondersButton.setGraphic(utilsView.smallIcon(AppearanceType.MALE_01.getResourcePath(), ""));

        showDepartmentStations(breadCrumbBar, navigationPanel, detailsPane, worldMap, department);
    }

    private void showDepartmentStations(BreadCrumbBar<Object> breadCrumbBar, Pane navigationPanel, Pane detailsPane, Pane worldMap, Department department) {
        // TODO: fix stuff readded to the map (background getting darker and darker)
        View.viewRunnable = () -> showDepartmentStations(breadCrumbBar, navigationPanel, detailsPane, worldMap, department);
        utilsView.clearPane(detailsPane);
        stationListView.show(breadCrumbBar, navigationPanel, detailsPane, worldMap, department.getStations());
    }

    private void showDepartmentDetails(Pane navigationPanel, Department department) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(labelPane, IconType.MEDIUM, IconColor.EMPTY, department.getDepartmentType().getResourcePath(), "");
        utilsView.addMainTitleLabel(labelPane, department.toString());
    }

    private void showDepartmentUnitCounts(Pane pane, Department department) {
        View.viewRunnable = () -> showDepartmentUnitCounts(pane, department);
        pane.getChildren().clear();
        utilsView.addSectionTitleLabel(pane, "Units");
        Map<UnitType, Map<UnitStatus, Long>> unitCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .collect(Collectors.groupingBy(
                                Unit::getUnitType,
                                Collectors.groupingBy(
                                        Unit::getUnitStatus,
                                        Collectors.counting())
                        )
                );

        switch (department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> {
                addUnitTypeCount(pane, unitCounts, FireUnitType.FIRE_ENGINE);
                addUnitTypeCount(pane, unitCounts, FireUnitType.FIRE_TRUCK);
                addUnitTypeCount(pane, unitCounts, FireUnitType.RESCUE_SQUAD);
            }
            case POLICE_DEPARTMENT -> {
                addUnitTypeCount(pane, unitCounts, PoliceUnitType.PATROL_UNIT);
                addUnitTypeCount(pane, unitCounts, PoliceUnitType.DETECTIVE_UNIT);
                addUnitTypeCount(pane, unitCounts, PoliceUnitType.HOMICIDE_UNIT);
                addUnitTypeCount(pane, unitCounts, PoliceUnitType.NARCOTICS_UNIT);
                addUnitTypeCount(pane, unitCounts, PoliceUnitType.VICE_UNIT);
            }
            case MEDIC_DEPARTMENT -> {
                addUnitTypeCount(pane, unitCounts, MedicUnitType.PRIMARY_CARE_UNIT);
                addUnitTypeCount(pane, unitCounts, MedicUnitType.CRITICAL_CARE_UNIT);
            }
        }
    }

    private void addUnitTypeCount(Pane pane, Map<UnitType, Map<UnitStatus, Long>> unitCounts, UnitType unitType) {
        Pane countPane = utilsView.createHBox(pane);
        Map<UnitStatus, Long> statusCounts = unitCounts.getOrDefault(unitType, Collections.emptyMap());
        utilsView.addIconToPane(countPane, IconType.SMALL, IconColor.EMPTY, unitType.getResourcePath(), unitType.toString());
        addUnitTypeStatusCount(countPane, statusCounts, UnitStatus.AVAILABLE);
        addUnitTypeStatusCount(countPane, statusCounts, UnitStatus.DISPATCHED);
        addUnitTypeStatusCount(countPane, statusCounts, UnitStatus.ON_SCENE);
        addUnitTypeStatusCount(countPane, statusCounts, UnitStatus.RETURNING);
        addUnitTypeStatusCount(countPane, statusCounts, UnitStatus.UNAVAILABLE);
    }

    private void addUnitTypeStatusCount(Pane pane, Map<UnitStatus, Long> units, UnitStatus unitStatus) {
        String count = " ";
        if (units.getOrDefault(unitStatus, 0L) > 0L ) {
            count = String.valueOf(units.getOrDefault(unitStatus, 0L));
        }
        utilsView.addBodySmallLabel(pane, count);
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, unitStatus.getResourcePath(), unitStatus.toString());
    }

    private void showDepartmentVehicleCounts(Pane pane, Department department) {
        View.viewRunnable = () -> showDepartmentVehicleCounts(pane, department);
        pane.getChildren().clear();
        utilsView.addSectionTitleLabel(pane, "Vehicles");
        Map<VehicleType, Map<VehicleStatus, Long>> vehicleCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getVehicles().stream())
                .collect(Collectors.groupingBy(
                                Vehicle::getVehicleType,
                                Collectors.groupingBy(
                                        Vehicle::getVehicleStatus,
                                        Collectors.counting())
                        )
                );

        switch (department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> {
                addVehicleTypeCount(pane, vehicleCounts, FireVehicleType.PUMPER);
                addVehicleTypeCount(pane, vehicleCounts, FireVehicleType.TANKER);
                addVehicleTypeCount(pane, vehicleCounts, FireVehicleType.LADDER);
                addVehicleTypeCount(pane, vehicleCounts, FireVehicleType.TOWER);
                addVehicleTypeCount(pane, vehicleCounts, FireVehicleType.RESCUE);
                addVehicleTypeCount(pane, vehicleCounts, FireVehicleType.HEAVY_RESCUE);
            }
            case POLICE_DEPARTMENT -> {
                addVehicleTypeCount(pane, vehicleCounts, PoliceVehicleType.SEDAN);
                addVehicleTypeCount(pane, vehicleCounts, PoliceVehicleType.SUV);
                addVehicleTypeCount(pane, vehicleCounts, PoliceVehicleType.MOTORCYCLE);
            }
            case MEDIC_DEPARTMENT -> {
                addVehicleTypeCount(pane, vehicleCounts, MedicVehicleType.BLS_AMBULANCE);
                addVehicleTypeCount(pane, vehicleCounts, MedicVehicleType.ALS_AMBULANCE);
            }
        }

    }

    private void addVehicleTypeCount(Pane pane, Map<VehicleType, Map<VehicleStatus, Long>> vehicleCounts, VehicleType vehicleType) {
        Pane countPane = utilsView.createHBox(pane);
        Map<VehicleStatus, Long> statusCounts = vehicleCounts.getOrDefault(vehicleType, Collections.emptyMap());
        utilsView.addIconToPane(countPane, IconType.SMALL, IconColor.EMPTY, vehicleType.getResourcePath(), vehicleType.toString());
        addVehicleTypeStatusCount(countPane, statusCounts, VehicleStatus.AVAILABLE);
        addVehicleTypeStatusCount(countPane, statusCounts, VehicleStatus.DISPATCHED);
        addVehicleTypeStatusCount(countPane, statusCounts, VehicleStatus.ON_SCENE);
        addVehicleTypeStatusCount(countPane, statusCounts, VehicleStatus.RETURNING);
        addVehicleTypeStatusCount(countPane, statusCounts, VehicleStatus.UNAVAILABLE);
    }

    private void addVehicleTypeStatusCount(Pane pane, Map<VehicleStatus, Long> vehicles, VehicleStatus vehicleStatus) {
        String count = " ";
        if (vehicles.getOrDefault(vehicleStatus, 0L) > 0L ) {
            count = String.valueOf(vehicles.getOrDefault(vehicleStatus, 0L));
        }
        utilsView.addBodySmallLabel(pane, count);
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, vehicleStatus.getResourcePath(), vehicleStatus.toString());
    }

    private void showDepartmentResponderCounts(Pane pane, Department department) {
        View.viewRunnable = () -> showDepartmentResponderCounts(pane, department);
        pane.getChildren().clear();
        utilsView.addSectionTitleLabel(pane, "Responders");
        Map<RankType, Map<ResponderStatus, Long>> responderCounts = department.getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .collect(Collectors.groupingBy(
                                responder -> responder.getRank().getRankType(),
                                Collectors.groupingBy(
                                        Responder::getResponderStatus,
                                        Collectors.counting())
                        )
                );

        switch (department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> {
                addRankTypeCount(pane, responderCounts, FireRankType.CANDIDATE_FIREFIGHTER);
                addRankTypeCount(pane, responderCounts, FireRankType.FIREFIGHTER);
                addRankTypeCount(pane, responderCounts, FireRankType.LIEUTENANT);
                addRankTypeCount(pane, responderCounts, FireRankType.CAPTAIN);
                addRankTypeCount(pane, responderCounts, FireRankType.BATTALION_CHIEF);
                addRankTypeCount(pane, responderCounts, FireRankType.DISTRICT_CHIEF);
                addRankTypeCount(pane, responderCounts, FireRankType.COMMISSIONER);
            }
            case POLICE_DEPARTMENT -> {
                addRankTypeCount(pane, responderCounts, PoliceRankType.OFFICER);
                addRankTypeCount(pane, responderCounts, PoliceRankType.SERGEANT);
                addRankTypeCount(pane, responderCounts, PoliceRankType.LIEUTENANT);
                addRankTypeCount(pane, responderCounts, PoliceRankType.CAPTAIN);
                addRankTypeCount(pane, responderCounts, PoliceRankType.COMMANDER);
                addRankTypeCount(pane, responderCounts, PoliceRankType.CHIEF);
                addRankTypeCount(pane, responderCounts, PoliceRankType.SUPERINTENDENT);
            }
            case MEDIC_DEPARTMENT -> {
                addRankTypeCount(pane, responderCounts, MedicRankType.EMR);
                addRankTypeCount(pane, responderCounts, MedicRankType.EMT);
                addRankTypeCount(pane, responderCounts, MedicRankType.PARAMEDIC);
                addRankTypeCount(pane, responderCounts, MedicRankType.PARAMEDIC_IN_CHARGE);
                addRankTypeCount(pane, responderCounts, MedicRankType.PARAMEDIC_SUPERVISOR);
                addRankTypeCount(pane, responderCounts, MedicRankType.PARAMEDIC_FIELD_CHIEF);
                addRankTypeCount(pane, responderCounts, MedicRankType.CHIEF_PARAMEDIC);
            }
        }

    }

    private void addRankTypeCount(Pane pane, Map<RankType, Map<ResponderStatus, Long>> responderCounts, RankType rankType) {
        Pane countPane = utilsView.createHBox(pane);
        Map<ResponderStatus, Long> statusCounts = responderCounts.getOrDefault(rankType, Collections.emptyMap());
        utilsView.addIconToPane(countPane, IconType.SMALL_BORDER, IconColor.BLACK, rankType.getResourcePath(), rankType.toString());
        addRankTypeStatusCount(countPane, statusCounts, ResponderStatus.AVAILABLE);
        addRankTypeStatusCount(countPane, statusCounts, ResponderStatus.DISPATCHED);
        addRankTypeStatusCount(countPane, statusCounts, ResponderStatus.ON_SCENE);
        addRankTypeStatusCount(countPane, statusCounts, ResponderStatus.RETURNING);
        addRankTypeStatusCount(countPane, statusCounts, ResponderStatus.UNAVAILABLE);
    }

    private void addRankTypeStatusCount(Pane pane, Map<ResponderStatus, Long> responders, ResponderStatus responderStatus) {
        String count = " ";
        if (responders.getOrDefault(responderStatus, 0L) > 0L ) {
            count = String.valueOf(responders.getOrDefault(responderStatus, 0L));
        }
        utilsView.addBodySmallLabel(pane, count);
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, responderStatus.getResourcePath(), responderStatus.toString());
    }

}