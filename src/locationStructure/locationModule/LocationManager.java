package locationStructure.locationModule;

import departmentStructure.departmentModule.DepartmentManager;

import java.util.ArrayList;
import java.util.List;

public class LocationManager {

    private List<Location> locations = new ArrayList<>();

    public LocationManager(DepartmentManager departmentManager) {
        System.out.println("LocationManager initializing");
		initLocations(departmentManager);
        System.out.println("LocationManager initialized successfully");
    }
	
	public Location getLocation(float latitude, float longitude) {
		// TODO
		return locations.get(0);
	}

	public void addLocation(Location location) {
		this.locations.add(location);
	}
	
	public void initLocations(DepartmentManager departmentManager) {
	
		// TODO: loop through Stations
		Location location = new Location(0.0f, 0.0f);  // TODO: random coordinates
		addLocation(location);
		// TODO
		//station.setLocation(location);
		
	}

}