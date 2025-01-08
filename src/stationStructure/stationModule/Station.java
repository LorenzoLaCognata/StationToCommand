package stationStructure.stationModule;

import departmentStructure.departmentModule.DepartmentType;
import locationStructure.locationModule.Location;
import unitStructure.unitModule.Unit;
import unitStructure.unitModule.UnitManager;

import java.util.List;

public class Station {

    private final DepartmentType departmentType;
    private final int number;
	private final Location location;
	private final UnitManager unitManager;
	
    public Station(DepartmentType departmentType, int number, Location location, StationManager stationManager) {
        this.departmentType = departmentType;
		this.number = number;
        this.location = location;
		this.unitManager = new UnitManager(departmentType, stationManager);
    }

    @Override
    public String toString() {
        return "[STATION] " + departmentType + " Station " + number;
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
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