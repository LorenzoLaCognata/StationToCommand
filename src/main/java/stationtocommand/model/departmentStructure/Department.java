package stationtocommand.model.departmentStructure;

import stationtocommand.model.personStructure.AppearanceType;
import stationtocommand.model.rankStructure.RankManager;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.shiftStructure.ShiftManager;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationManager;
import stationtocommand.model.stationStructure.StationType;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitStatus;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Department {

    private final DepartmentType departmentType;
    private final StationManager stationManager;
    private final RankManager rankManager;
    private final ShiftManager shiftManager;

    public Department(DepartmentType departmentType) {
        this.departmentType = departmentType;
        this.stationManager = new StationManager(this);
        this.rankManager = new RankManager(this);
        this.shiftManager = new ShiftManager(this);
    }

    @Override
    public String toString() {
        return departmentType + " Department";
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    public StationManager getStationManager() {
        return stationManager;
    }

    public List<Station> getStations() {
        return stationManager.getStations();
    }

    public RankManager getRankManager() {
        return rankManager;
    }

    public ShiftManager getShiftManager() {
        return shiftManager;
    }

    public StationType defaultStationType() {
        return switch(departmentType) {
                case FIRE_DEPARTMENT -> StationType.FIRE_STATION;
                case POLICE_DEPARTMENT -> StationType.POLICE_STATION;
                case MEDIC_DEPARTMENT -> StationType.MEDIC_STATION;
            };
    }

    public UnitType defaultUnitType() {
        return switch(departmentType) {
            case FIRE_DEPARTMENT -> FireUnitType.values()[0];
            case POLICE_DEPARTMENT -> PoliceUnitType.values()[0];
            case MEDIC_DEPARTMENT -> MedicUnitType.values()[0];
        };
    }

    public VehicleType defaultVehicleType() {
        return switch(departmentType) {
            case FIRE_DEPARTMENT -> FireVehicleType.values()[0];
            case POLICE_DEPARTMENT -> PoliceVehicleType.values()[0];
            case MEDIC_DEPARTMENT -> MedicVehicleType.values()[0];
        };
    }

    public AppearanceType defaultAppearanceType() {
        Responder chiefResponder = getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .max(Comparator.comparing(responder -> responder.getRank().getRankType().getLevel()))
                .orElse(null);
        return chiefResponder != null ? chiefResponder.getAppearanceType() : AppearanceType.MALE_01;
    }

    public List<UnitType> unitTypesList() {
        return switch (departmentType) {
            case DepartmentType.FIRE_DEPARTMENT -> List.of(FireUnitType.values());
            case DepartmentType.POLICE_DEPARTMENT -> List.of(PoliceUnitType.values());
            case DepartmentType.MEDIC_DEPARTMENT -> List.of(MedicUnitType.values());
        };
    }

    public Map<UnitStatus, Long> unitsByStatus() {
        return getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .collect(Collectors.groupingBy(
                        Unit::getUnitStatus,
                        Collectors.counting())
                );
    }

    public Map<UnitType, Map<UnitStatus, Long>> unitsByTypeAndStatus() {
        return getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .collect(Collectors.groupingBy(
                                Unit::getUnitType,
                                Collectors.groupingBy(
                                        Unit::getUnitStatus,
                                        Collectors.counting())
                        )
                );
    }

    public Map<VehicleStatus, Long> vehiclesByStatus() {
        return getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getVehicles().stream())
                .collect(Collectors.groupingBy(
                        Vehicle::getVehicleStatus,
                        Collectors.counting())
                );
    }

    public Map<VehicleType, Map<VehicleStatus, Long>> vehiclesByTypeAndStatus() {
        return getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getVehicles().stream())
                .collect(Collectors.groupingBy(
                                Vehicle::getVehicleType,
                                Collectors.groupingBy(
                                        Vehicle::getVehicleStatus,
                                        Collectors.counting())
                        )
                );
    }

    public Map<ResponderStatus, Long> respondersByStatus() {
        return getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .collect(Collectors.groupingBy(
                        Responder::getResponderStatus,
                        Collectors.counting())
                );
    }

    public Map<RankType, Map<ResponderStatus, Long>> respondersByRankAndStatus() {
        return getStations().stream()
                .flatMap(station -> station.getUnits().stream())
                .flatMap(unit -> unit.getResponders().stream())
                .collect(Collectors.groupingBy(
                                responder -> responder.getRank().getRankType(),
                                Collectors.groupingBy(
                                        Responder::getResponderStatus,
                                        Collectors.counting())
                        )
                );
    }

}