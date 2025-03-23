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

    public void show(BreadCrumbBar<Object> breadCrumbBar, View view, Department department) {
        View.viewRunnable = () -> show(breadCrumbBar, view, department);
        utilsView.addBreadCrumb(breadCrumbBar, department);
        view.getNavigationPanel().clear();
        view.getWorldMap().clear();
        showDepartmentDetails(view, department);
        stationListView.show(breadCrumbBar, view, department.getStations());
        showDepartmentUnitCounts(view, department);
        showDepartmentVehicleCounts(view, department);
        showDepartmentResponderCounts(view, department);
    }

    private void showDepartmentDetails(View view, Department department) {
        Pane horizontalTitlePane = utilsView.createHBox(view.getNavigationPanel().getTitlePane());
        utilsView.addIconToPane(horizontalTitlePane, IconType.MEDIUM, IconColor.EMPTY, department.getDepartmentType().getResourcePath(), "");
        utilsView.addMainTitleLabel(horizontalTitlePane, department.toString());
    }

    private void showDepartmentUnitCounts(View view, Department department) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Units");
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
                addUnitTypeCount(view, unitCounts, FireUnitType.FIRE_ENGINE);
                addUnitTypeCount(view, unitCounts, FireUnitType.FIRE_TRUCK);
                addUnitTypeCount(view, unitCounts, FireUnitType.RESCUE_SQUAD);
            }
            case POLICE_DEPARTMENT -> {
                addUnitTypeCount(view, unitCounts, PoliceUnitType.PATROL_UNIT);
                addUnitTypeCount(view, unitCounts, PoliceUnitType.DETECTIVE_UNIT);
                addUnitTypeCount(view, unitCounts, PoliceUnitType.HOMICIDE_UNIT);
                addUnitTypeCount(view, unitCounts, PoliceUnitType.NARCOTICS_UNIT);
                addUnitTypeCount(view, unitCounts, PoliceUnitType.VICE_UNIT);
            }
            case MEDIC_DEPARTMENT -> {
                addUnitTypeCount(view, unitCounts, MedicUnitType.PRIMARY_CARE_UNIT);
                addUnitTypeCount(view, unitCounts, MedicUnitType.CRITICAL_CARE_UNIT);
            }
        }
    }

    private void addUnitTypeCount(View view, Map<UnitType, Map<UnitStatus, Long>> unitCounts, UnitType unitType) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        Map<UnitStatus, Long> statusCounts = unitCounts.getOrDefault(unitType, Collections.emptyMap());
        utilsView.addIconToPane(horizontalDetailsPane, IconType.SMALL, IconColor.EMPTY, unitType.getResourcePath(), unitType.toString());
        addUnitTypeStatusCount(horizontalDetailsPane, statusCounts, UnitStatus.AVAILABLE);
        addUnitTypeStatusCount(horizontalDetailsPane, statusCounts, UnitStatus.DISPATCHED);
        addUnitTypeStatusCount(horizontalDetailsPane, statusCounts, UnitStatus.ON_SCENE);
        addUnitTypeStatusCount(horizontalDetailsPane, statusCounts, UnitStatus.RETURNING);
        addUnitTypeStatusCount(horizontalDetailsPane, statusCounts, UnitStatus.UNAVAILABLE);
    }

    private void addUnitTypeStatusCount(Pane pane, Map<UnitStatus, Long> units, UnitStatus unitStatus) {
        String count = " ";
        if (units.getOrDefault(unitStatus, 0L) > 0L ) {
            count = String.valueOf(units.getOrDefault(unitStatus, 0L));
        }
        utilsView.addBodySmallLabel(pane, count);
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, unitStatus.getResourcePath(), unitStatus.toString());
    }

    private void showDepartmentVehicleCounts(View view, Department department) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Vehicles");
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
                addVehicleTypeCount(view, vehicleCounts, FireVehicleType.PUMPER);
                addVehicleTypeCount(view, vehicleCounts, FireVehicleType.TANKER);
                addVehicleTypeCount(view, vehicleCounts, FireVehicleType.LADDER);
                addVehicleTypeCount(view, vehicleCounts, FireVehicleType.TOWER);
                addVehicleTypeCount(view, vehicleCounts, FireVehicleType.RESCUE);
                addVehicleTypeCount(view, vehicleCounts, FireVehicleType.HEAVY_RESCUE);
            }
            case POLICE_DEPARTMENT -> {
                addVehicleTypeCount(view, vehicleCounts, PoliceVehicleType.SEDAN);
                addVehicleTypeCount(view, vehicleCounts, PoliceVehicleType.SUV);
                addVehicleTypeCount(view, vehicleCounts, PoliceVehicleType.MOTORCYCLE);
            }
            case MEDIC_DEPARTMENT -> {
                addVehicleTypeCount(view, vehicleCounts, MedicVehicleType.BLS_AMBULANCE);
                addVehicleTypeCount(view, vehicleCounts, MedicVehicleType.ALS_AMBULANCE);
            }
        }

    }

    private void addVehicleTypeCount(View view, Map<VehicleType, Map<VehicleStatus, Long>> vehicleCounts, VehicleType vehicleType) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        Map<VehicleStatus, Long> statusCounts = vehicleCounts.getOrDefault(vehicleType, Collections.emptyMap());
        utilsView.addIconToPane(horizontalDetailsPane, IconType.SMALL, IconColor.EMPTY, vehicleType.getResourcePath(), vehicleType.toString());
        addVehicleTypeStatusCount(horizontalDetailsPane, statusCounts, VehicleStatus.AVAILABLE);
        addVehicleTypeStatusCount(horizontalDetailsPane, statusCounts, VehicleStatus.DISPATCHED);
        addVehicleTypeStatusCount(horizontalDetailsPane, statusCounts, VehicleStatus.ON_SCENE);
        addVehicleTypeStatusCount(horizontalDetailsPane, statusCounts, VehicleStatus.RETURNING);
        addVehicleTypeStatusCount(horizontalDetailsPane, statusCounts, VehicleStatus.UNAVAILABLE);
    }

    private void addVehicleTypeStatusCount(Pane pane, Map<VehicleStatus, Long> vehicles, VehicleStatus vehicleStatus) {
        String count = " ";
        if (vehicles.getOrDefault(vehicleStatus, 0L) > 0L ) {
            count = String.valueOf(vehicles.getOrDefault(vehicleStatus, 0L));
        }
        utilsView.addBodySmallLabel(pane, count);
        utilsView.addIconToPane(pane, IconType.SMALL, IconColor.EMPTY, vehicleStatus.getResourcePath(), vehicleStatus.toString());
    }

    private void showDepartmentResponderCounts(View view, Department department) {
        utilsView.addSectionTitleLabel(view.getNavigationPanel().getDetailsPane(), "Responders");
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
                addRankTypeCount(view, responderCounts, FireRankType.CANDIDATE_FIREFIGHTER);
                addRankTypeCount(view, responderCounts, FireRankType.FIREFIGHTER);
                addRankTypeCount(view, responderCounts, FireRankType.LIEUTENANT);
                addRankTypeCount(view, responderCounts, FireRankType.CAPTAIN);
                addRankTypeCount(view, responderCounts, FireRankType.BATTALION_CHIEF);
                addRankTypeCount(view, responderCounts, FireRankType.DISTRICT_CHIEF);
                addRankTypeCount(view, responderCounts, FireRankType.COMMISSIONER);
            }
            case POLICE_DEPARTMENT -> {
                addRankTypeCount(view, responderCounts, PoliceRankType.OFFICER);
                addRankTypeCount(view, responderCounts, PoliceRankType.SERGEANT);
                addRankTypeCount(view, responderCounts, PoliceRankType.LIEUTENANT);
                addRankTypeCount(view, responderCounts, PoliceRankType.CAPTAIN);
                addRankTypeCount(view, responderCounts, PoliceRankType.COMMANDER);
                addRankTypeCount(view, responderCounts, PoliceRankType.CHIEF);
                addRankTypeCount(view, responderCounts, PoliceRankType.SUPERINTENDENT);
            }
            case MEDIC_DEPARTMENT -> {
                addRankTypeCount(view, responderCounts, MedicRankType.EMR);
                addRankTypeCount(view, responderCounts, MedicRankType.EMT);
                addRankTypeCount(view, responderCounts, MedicRankType.PARAMEDIC);
                addRankTypeCount(view, responderCounts, MedicRankType.PARAMEDIC_IN_CHARGE);
                addRankTypeCount(view, responderCounts, MedicRankType.PARAMEDIC_SUPERVISOR);
                addRankTypeCount(view, responderCounts, MedicRankType.PARAMEDIC_FIELD_CHIEF);
                addRankTypeCount(view, responderCounts, MedicRankType.CHIEF_PARAMEDIC);
            }
        }

    }

    private void addRankTypeCount(View view, Map<RankType, Map<ResponderStatus, Long>> responderCounts, RankType rankType) {
        Pane horizontalDetailsPane = utilsView.createHBox(view.getNavigationPanel().getDetailsPane());
        Map<ResponderStatus, Long> statusCounts = responderCounts.getOrDefault(rankType, Collections.emptyMap());
        utilsView.addIconToPane(horizontalDetailsPane, IconType.SMALL_BORDER, IconColor.BLACK, rankType.getResourcePath(), rankType.toString());
        addRankTypeStatusCount(horizontalDetailsPane, statusCounts, ResponderStatus.AVAILABLE);
        addRankTypeStatusCount(horizontalDetailsPane, statusCounts, ResponderStatus.DISPATCHED);
        addRankTypeStatusCount(horizontalDetailsPane, statusCounts, ResponderStatus.ON_SCENE);
        addRankTypeStatusCount(horizontalDetailsPane, statusCounts, ResponderStatus.RETURNING);
        addRankTypeStatusCount(horizontalDetailsPane, statusCounts, ResponderStatus.UNAVAILABLE);
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