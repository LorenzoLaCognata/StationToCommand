package stationtocommand.model.stationStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitManager;
import stationtocommand.model.unitStructure.UnitStatus;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleStatus;
import stationtocommand.model.vehicleStructure.VehicleType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Station implements Comparable<Station> {

    private final Department department;
    private final StationType stationType;
    private final int number;
	private final Location location;
	private final UnitManager unitManager;
	
    public Station(Department department, int number, Location location, StationManager stationManager) {
        this.department = department;
        switch (department.getDepartmentType()) {
            case FIRE_DEPARTMENT -> this.stationType = StationType.FIRE_STATION;
            case POLICE_DEPARTMENT -> this.stationType = StationType.POLICE_STATION;
            case MEDIC_DEPARTMENT -> this.stationType = StationType.MEDIC_STATION;
            default ->  stationType = null;
        }
		this.number = number;
        this.location = location;
		this.unitManager = new UnitManager(this, stationManager);
    }

    @Override
    public String toString() {
        return department.getDepartmentType() + " Station " + number;
    }

    @Override
    public int compareTo(Station other) {
        int result = Integer.compare(this.stationType.ordinal(), other.getStationType().ordinal());
        if (result != 0) {
            return result;
        }
        result = Integer.compare(this.number, other.getNumber());
        if (result != 0) {
            return result;
        }
        return Integer.compare(System.identityHashCode(this), System.identityHashCode(other));
    }

    public Department getDepartment() {
        return department;
    }

    public StationType getStationType() {
        return stationType;
    }

    public int getNumber() {
        return number;
    }

    public Location getLocation() {
        return location;
    }

    public UnitManager getUnitManager() {
        return unitManager;
    }

    public List<Unit> getUnits() {
        return unitManager.getUnits();
    }

    public Map<UnitStatus, Long> unitsByStatus() {
        return getUnits().stream()
                .collect(Collectors.groupingBy(
                        Unit::getUnitStatus,
                        Collectors.counting())
                );
    }

    public Map<UnitType, Map<UnitStatus, Long>> unitsByTypeAndStatus() {
        return getUnits().stream()
                .collect(Collectors.groupingBy(
                        Unit::getUnitType,
                        Collectors.groupingBy(
                            Unit::getUnitStatus,
                            Collectors.counting())
                    )
                );
    }

    public Map<VehicleStatus, Long> vehiclesByStatus() {
        return getUnits().stream()
                .flatMap(unit -> unit.getVehicles().stream())
                .collect(Collectors.groupingBy(
                        Vehicle::getVehicleStatus,
                        Collectors.counting())
                );
    }

    public Map<VehicleType, Map<VehicleStatus, Long>> vehiclesByTypeAndStatus() {
        return getUnits().stream()
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
        return getUnits().stream()
                    .flatMap(unit -> unit.getResponders().stream())
                    .collect(Collectors.groupingBy(
                            Responder::getResponderStatus,
                            Collectors.counting())
                    );
    }

    public Map<RankType, Map<ResponderStatus, Long>> respondersByRankAndStatus() {
        return getUnits().stream()
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