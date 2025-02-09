package model.stationStructure;

import model.departmentStructure.Department;
import model.locationStructure.Location;
import model.responderStructure.Responder;
import model.responderStructure.ResponderLink;
import model.unitStructure.Unit;
import model.unitStructure.UnitManager;
import model.vehicleStructure.Vehicle;
import model.vehicleStructure.VehicleLink;

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

    public List<Responder> getResponders() {
        return unitManager.getUnits().stream()
                .flatMap(unit -> unit.getResponderLinks().stream())
                .map(ResponderLink::getResponder)
                .toList();
    }

    public List<Vehicle> getVehicles() {
        return unitManager.getUnits().stream()
                .flatMap(unit -> unit.getVehicleLinks().stream())
                .map(VehicleLink::getVehicle)
                .toList();
    }

}