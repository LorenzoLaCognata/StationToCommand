package vehicleStructure.vehicleModule;

import departmentStructure.departmentModule.DepartmentManager;
import unitStructure.unitModule.FireUnit;
import unitStructure.unitTypeModule.FireUnitType;

import java.util.ArrayList;
import java.util.List;

public class VehicleManager {

    private List<Vehicle> vehicles = new ArrayList<>();

    public VehicleManager(DepartmentManager departmentManager) {
			System.out.println("VehicleManager initializing");
			initVehicles(departmentManager);
			System.out.println("VehicleManager initialized successfully");
    }
	
	public Vehicle getVehicle() {
		// TODO
		return vehicles.get(0);
	}

	public void addVehicle(Vehicle Vehicle) {
		this.vehicles.add(Vehicle);
	}

	public void initVehicles(DepartmentManager departmentManager) {
		
		// TODO: loop through Stations and Units
		FireUnit unit = new FireUnit(FireUnitType.FireEngine);
		addVehicle(new Vehicle(unit));

	}

}