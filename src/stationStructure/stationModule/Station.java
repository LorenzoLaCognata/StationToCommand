package stationStructure.stationModule;

import departmentStructure.departmentModule.DepartmentType;
import locationStructure.locationModule.Location;
import unitStructure.unitModule.UnitManager;

public class Station {

    private int number;
	private Location location;
	private UnitManager unitManager;
	
    public Station(DepartmentType departmentType) {
        System.out.println("Station initializing");
		this.number = 1; // TO DO: station counter
		//this.location = new Location(...);
		this.unitManager = new UnitManager(departmentType);
        System.out.println("Station initialized successfully");
    }

}