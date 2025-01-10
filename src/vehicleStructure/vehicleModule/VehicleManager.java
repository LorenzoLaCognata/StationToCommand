package vehicleStructure.vehicleModule;

import departmentStructure.departmentModule.Department;
import departmentStructure.departmentModule.DepartmentManager;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.FireUnit;
import unitStructure.unitTypeModule.FireUnitType;

import java.util.ArrayList;
import java.util.List;

public class VehicleManager {

    private final List<Vehicle> vehicles = new ArrayList<>();

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
		Department department = departmentManager.getDepartments().getFirst();
		Station station = department.getStations().getFirst();
		FireUnit unit = new FireUnit(station, FireUnitType.FIRE_ENGINE, 0);
		addVehicle(new Vehicle(unit));

	}

}