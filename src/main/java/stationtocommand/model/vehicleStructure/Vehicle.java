package stationtocommand.model.vehicleStructure;

import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitLink;

public class Vehicle implements Comparable<Vehicle> {

	private final VehicleType vehicleType;
	private VehicleStatus vehicleStatus;
	private float integrity = 1.0f;
	private float condition = 1.0f;
	private Location location;
	private UnitLink unitLink;

    public Vehicle(VehicleType vehicleType, Unit unit) {
		this.vehicleType = vehicleType;
		this.vehicleStatus = VehicleStatus.AVAILABLE;
		this.location = unit.getStation().getLocation();
		this.unitLink = new VehicleUnitLink(unit);
		unit.linkVehicle(this);
    }

	@Override
	public String toString() {
		return vehicleType.toString();
	}

	@Override
	public int compareTo(Vehicle other) {
		return Integer.compare(((Enum<?>) this.vehicleType).ordinal(), ((Enum<?>) other.getVehicleType()).ordinal());
	}

	public VehicleType getVehicleType() {
		return vehicleType;
	}

	public VehicleStatus getVehicleStatus() {
		return vehicleStatus;
	}

	public void setVehicleStatus(VehicleStatus vehicleStatus) {
		this.vehicleStatus = vehicleStatus;
	}

	public Location getLocation() {
		return location;
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
		unit.linkVehicle(this);
	}

	
}