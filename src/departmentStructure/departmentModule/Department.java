package departmentStructure.departmentModule;

import stationStructure.stationModule.StationManager;

public class Department {

    private DepartmentType departmentType;
	private StationManager stationManager;
	
    public Department(DepartmentType departmentType) {
        System.out.println("Department initializing");
		this.departmentType = departmentType;
		this.stationManager = new StationManager(this.departmentType);
        System.out.println("Department initialized successfully");
    }

}