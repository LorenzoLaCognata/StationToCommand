package vehicleStructure.vehicleModule;

import locationStructure.locationModule.Location;
import unitStructure.unitModule.Unit;
import linkStructure.organizationLinkModule.UnitLink;

public class Vehicle {

	private Location Location;
	private UnitLink unitLink;

    public Vehicle(Unit unit) {
        System.out.println("Vehicle initializing");
		//this.location = new Location(); // TODO
		linkUnit(unit);
        System.out.println("Vehicle initialized successfully");
    }

	public void updateLocation(Location location) {
		// TODO
	}
	
	public void linkUnit(Unit unit) {
		// TODO
	}

	
}