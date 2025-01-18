package vehicleStructure.vehicleModule;

import linkStructure.organizationLinkModule.UnitLink;
import locationStructure.locationModule.Location;
import unitStructure.unitModule.Unit;
import vehicleStructure.vehicleLinkModule.VehicleUnitLink;

public class Vehicle {

	private final VehicleType vehicleType;
	private Location location;
	private UnitLink unitLink;

    public Vehicle(VehicleType vehicleType, Unit unit) {
		this.vehicleType = vehicleType;
		this.location = unit.getStation().getLocation();
		this.unitLink = new VehicleUnitLink(unit);
    }

	@Override
	public String toString() {
		return "[VEHICLE] " + this.vehicleType;
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public UnitLink getUnitLink() {
		return unitLink;
	}

	public void linkUnit(Unit unit) {
		this.unitLink = new VehicleUnitLink(unit);
	}

	
}