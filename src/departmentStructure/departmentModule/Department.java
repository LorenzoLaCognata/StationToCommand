package departmentStructure.departmentModule;

import locationStructure.locationModule.LocationManager;
import stationStructure.stationModule.StationManager;

public class Department {

    private final DepartmentType departmentType;
	private final StationManager stationManager;
	
    public Department(DepartmentType departmentType, LocationManager locationManager) {
        System.out.println("Department initializing");
		this.departmentType = departmentType;
		this.stationManager = new StationManager(departmentType, locationManager);
        System.out.println("Department initialized successfully: \n" + this);
    }

    @Override
    public String toString() {
        return "[DEPARTMENT] " + departmentType + " Department" + "\n" + stationManager;
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

}