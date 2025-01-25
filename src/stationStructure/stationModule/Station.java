package stationStructure.stationModule;

import departmentStructure.departmentModule.Department;
import linkStructure.personLinkModule.ResponderLink;
import linkStructure.skillLinkModule.SkillLink;
import linkStructure.vehicleLinkModule.VehicleLink;
import locationStructure.locationModule.Location;
import responderStructure.responderModule.Responder;
import unitStructure.unitLinkModule.UnitResponderLink;
import unitStructure.unitModule.Unit;
import unitStructure.unitModule.UnitManager;
import vehicleStructure.vehicleModule.Vehicle;

import java.util.List;
import java.util.stream.Collectors;

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
                .collect(Collectors.toList());
    }

    public List<Vehicle> getVehicles() {
        return unitManager.getUnits().stream()
                .flatMap(unit -> unit.getVehicleLinks().stream())
                .map(VehicleLink::getVehicle)
                .collect(Collectors.toList());
    }

}