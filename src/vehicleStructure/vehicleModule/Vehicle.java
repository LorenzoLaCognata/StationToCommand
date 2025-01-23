package vehicleStructure.vehicleModule;

import linkStructure.organizationLinkModule.UnitLink;
import locationStructure.locationModule.Location;
import unitStructure.unitModule.Unit;
import vehicleStructure.vehicleLinkModule.VehicleUnitLink;

public class Vehicle {

	private final VehicleType vehicleType;
	private float integrity = 1.0f;
	private float condition = 1.0f;
	private Location location;
	private UnitLink unitLink;

    public Vehicle(VehicleType vehicleType, Unit unit) {
		this.vehicleType = vehicleType;
		this.location = unit.getStation().getLocation();
		this.unitLink = new VehicleUnitLink(unit);
    }

	@Override
	public String toString() {
		return vehicleType.toString();
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

	public float getIntegrity() {
		return integrity;
	}

	public void setIntegrity(float integrity) {
		this.integrity = integrity;
	}

	public float getCondition() {
		return condition;
	}

	public void setCondition(float condition) {
		this.condition = condition;
	}

	public void linkUnit(Unit unit) {
		this.unitLink = new VehicleUnitLink(unit);
	}

	
}