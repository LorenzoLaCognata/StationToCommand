package vehicleStructure.vehicleModule;

import linkStructure.organizationLinkModule.UnitLink;
import locationStructure.locationModule.Location;
import unitStructure.unitModule.Unit;
import vehicleStructure.vehicleLinkModule.VehicleUnitLink;

public class Vehicle {

	private Location Location;
	private final UnitLink unitLink;

    public Vehicle(Unit unit) {
        System.out.println("Vehicle initializing");
		//this.location = new Location(); // TODO
		this.unitLink = new VehicleUnitLink(unit);
        System.out.println("Vehicle initialized successfully");
    }

	public void updateLocation(Location location) {
		// TODO
	}
	
	public void linkUnit(Unit unit) {
		// TODO
	}

	
}