package stationStructure.stationModule;

import departmentStructure.departmentModule.Department;
import locationStructure.locationModule.Location;
import unitStructure.unitModule.Unit;
import unitStructure.unitModule.UnitManager;

import java.util.List;

public class Station {

    private final Department department;
    private final int number;
	private final Location location;
	private final UnitManager unitManager;
	
    public Station(Department department, int number, Location location, StationManager stationManager) {
        this.department = department;
		this.number = number;
        this.location = location;
		this.unitManager = new UnitManager(this, stationManager);
    }

    @Override
    public String toString() {
        return department.getDepartmentType() + " Station " + number;
    }

    public Department getDepartment() {
        return department;
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