package stationtocommand.model.stationStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitManager;

import java.util.List;

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
        int result = Integer.compare(((Enum<?>) this.stationType).ordinal(), ((Enum<?>) other.getStationType()).ordinal());
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

}