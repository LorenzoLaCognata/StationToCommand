package stationtocommand.view.departmentStructure;

import javafx.scene.layout.Pane;
import org.controlsfx.control.BreadCrumbBar;
import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.rankTypeStructure.FireRankType;
import stationtocommand.model.rankTypeStructure.MedicRankType;
import stationtocommand.model.rankTypeStructure.PoliceRankType;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
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
        stationListView.show(breadCrumbBar, navigationPanel, worldMap, department.getStations());
        showDepartmentUnitCounts(navigationPanel, department);
        showDepartmentVehicleCounts(navigationPanel, department);
        showDepartmentResponderCounts(navigationPanel, department);
    }

    private void showDepartmentDetails(Pane navigationPanel, Department department) {
        Pane labelPane = utilsView.createHBox(navigationPanel);
        utilsView.addIconToPane(navigationPanel, IconType.MEDIUM, IconColor.EMPTY, department.getDepartmentType().getResourcePath(), "");
        utilsView.addMainTitleLabel(labelPane, department.toString());
    }

    private void showDepartmentUnitCounts(Pane navigationPanel, Department department) {
        utilsView.addSectionTitleLabel(navigationPanel, "Units");
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
                addUnitTypeCount(navigationPanel, unitCounts, FireUnitType.FIRE_ENGINE);
                addUnitTypeCount(navigationPanel, unitCounts, FireUnitType.FIRE_TRUCK);
                addUnitTypeCount(navigationPanel, unitCounts, FireUnitType.RESCUE_SQUAD);
            }
            case POLICE_DEPARTMENT -> {
                addUnitTypeCount(navigationPanel, unitCounts, PoliceUnitType.PATROL_UNIT);
                addUnitTypeCount(navigationPanel, unitCounts, PoliceUnitType.DETECTIVE_UNIT);
                addUnitTypeCount(navigationPanel, unitCounts, PoliceUnitType.HOMICIDE_UNIT);
                addUnitTypeCount(navigationPanel, unitCounts, PoliceUnitType.NARCOTICS_UNIT);
                addUnitTypeCount(navigationPanel, unitCounts, PoliceUnitType.VICE_UNIT);
            }
            case MEDIC_DEPARTMENT -> {
                addUnitTypeCount(navigationPanel, unitCounts, MedicUnitType.PRIMARY_CARE_UNIT);
                addUnitTypeCount(navigationPanel, unitCounts, MedicUnitType.CRITICAL_CARE_UNIT);
            }
        }
    }

    private void addUnitTypeCount(Pane navigationPanel, Map<UnitType, Map<UnitStatus, Long>> unitCounts, UnitType unitType) {
        Pane countPane = utilsView.createHBox(navigationPanel);
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

    private void showDepartmentVehicleCounts(Pane navigationPanel, Department department) {
        utilsView.addSectionTitleLabel(navigationPanel, "Vehicles");
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
                addVehicleTypeCount(navigationPanel, vehicleCounts, FireVehicleType.PUMPER);
                addVehicleTypeCount(navigationPanel, vehicleCounts, FireVehicleType.TANKER);
                addVehicleTypeCount(navigationPanel, vehicleCounts, FireVehicleType.LADDER);
                addVehicleTypeCount(navigationPanel, vehicleCounts, FireVehicleType.TOWER);
                addVehicleTypeCount(navigationPanel, vehicleCounts, FireVehicleType.RESCUE);
                addVehicleTypeCount(navigationPanel, vehicleCounts, FireVehicleType.HEAVY_RESCUE);
            }
            case POLICE_DEPARTMENT -> {
                addVehicleTypeCount(navigationPanel, vehicleCounts, PoliceVehicleType.SEDAN);
                addVehicleTypeCount(navigationPanel, vehicleCounts, PoliceVehicleType.SUV);
                addVehicleTypeCount(navigationPanel, vehicleCounts, PoliceVehicleType.MOTORCYCLE);
            }
            case MEDIC_DEPARTMENT -> {
                addVehicleTypeCount(navigationPanel, vehicleCounts, MedicVehicleType.BLS_AMBULANCE);
                addVehicleTypeCount(navigationPanel, vehicleCounts, MedicVehicleType.ALS_AMBULANCE);
            }
        }

    }

    private void addVehicleTypeCount(Pane navigationPanel, Map<VehicleType, Map<VehicleStatus, Long>> vehicleCounts, VehicleType vehicleType) {
        Pane countPane = utilsView.createHBox(navigationPanel);
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

    private void showDepartmentResponderCounts(Pane navigationPanel, Department department) {
        utilsView.addSectionTitleLabel(navigationPanel, "Responders");
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
                addRankTypeCount(navigationPanel, responderCounts, FireRankType.CANDIDATE_FIREFIGHTER);
                addRankTypeCount(navigationPanel, responderCounts, FireRankType.FIREFIGHTER);
                addRankTypeCount(navigationPanel, responderCounts, FireRankType.LIEUTENANT);
                addRankTypeCount(navigationPanel, responderCounts, FireRankType.CAPTAIN);
                addRankTypeCount(navigationPanel, responderCounts, FireRankType.BATTALION_CHIEF);
                addRankTypeCount(navigationPanel, responderCounts, FireRankType.DISTRICT_CHIEF);
                addRankTypeCount(navigationPanel, responderCounts, FireRankType.COMMISSIONER);
            }
            case POLICE_DEPARTMENT -> {
                addRankTypeCount(navigationPanel, responderCounts, PoliceRankType.OFFICER);
                addRankTypeCount(navigationPanel, responderCounts, PoliceRankType.SERGEANT);
                addRankTypeCount(navigationPanel, responderCounts, PoliceRankType.LIEUTENANT);
                addRankTypeCount(navigationPanel, responderCounts, PoliceRankType.CAPTAIN);
                addRankTypeCount(navigationPanel, responderCounts, PoliceRankType.COMMANDER);
                addRankTypeCount(navigationPanel, responderCounts, PoliceRankType.CHIEF);
                addRankTypeCount(navigationPanel, responderCounts, PoliceRankType.SUPERINTENDENT);
            }
            case MEDIC_DEPARTMENT -> {
                addRankTypeCount(navigationPanel, responderCounts, MedicRankType.EMR);
                addRankTypeCount(navigationPanel, responderCounts, MedicRankType.EMT);
                addRankTypeCount(navigationPanel, responderCounts, MedicRankType.PARAMEDIC);
                addRankTypeCount(navigationPanel, responderCounts, MedicRankType.PARAMEDIC_IN_CHARGE);
                addRankTypeCount(navigationPanel, responderCounts, MedicRankType.PARAMEDIC_SUPERVISOR);
                addRankTypeCount(navigationPanel, responderCounts, MedicRankType.PARAMEDIC_FIELD_CHIEF);
                addRankTypeCount(navigationPanel, responderCounts, MedicRankType.CHIEF_PARAMEDIC);
            }
        }

    }

    private void addRankTypeCount(Pane navigationPanel, Map<RankType, Map<ResponderStatus, Long>> responderCounts, RankType rankType) {
        Pane countPane = utilsView.createHBox(navigationPanel);
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