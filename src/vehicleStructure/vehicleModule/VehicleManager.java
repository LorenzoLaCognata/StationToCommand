package vehicleStructure.vehicleModule;

import departmentStructure.departmentModule.Department;
import departmentStructure.departmentModule.DepartmentManager;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.Unit;
import unitStructure.unitTypeModule.FireUnitType;
import unitStructure.unitTypeModule.MedicUnitType;
import unitStructure.unitTypeModule.PoliceUnitType;

import java.util.ArrayList;
import java.util.List;

public class VehicleManager {

    private final List<Vehicle> vehicles = new ArrayList<>();

    public VehicleManager(DepartmentManager departmentManager) {
			initVehicles(departmentManager);
    }

	@Override
	public String toString() {
		return this.vehicles.toString();
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public Vehicle getVehicle(VehicleType vehicleType) {
		return vehicles.stream()
				.filter(item -> item.getVehicleType().equals(vehicleType))
				.findFirst()
				.orElse(null);
	}

	public Vehicle getVehicle(Unit unit) {
		return vehicles.stream()
				.filter(item -> item.getUnitLink().getUnit().equals(unit))
				.findFirst()
				.orElse(null);
	}

	public void addVehicle(Vehicle Vehicle) {
		this.vehicles.add(Vehicle);
	}

	public void initVehicles(DepartmentManager departmentManager) {
		
		for (Department department: departmentManager.getDepartments()) {
			for (Station station : department.getStations()) {
				for (Unit unit : station.getUnits()) {
                    switch (unit.getUnitType()) {
						case FireUnitType.FIRE_ENGINE -> addVehicle(new Vehicle(FireVehicleType.PUMPER, unit));
						case FireUnitType.FIRE_TRUCK -> addVehicle(new Vehicle(FireVehicleType.LADDER, unit));
                        case FireUnitType.RESCUE_SQUAD -> addVehicle(new Vehicle(FireVehicleType.HEAVY_RESCUE, unit));
						case PoliceUnitType.PATROL_UNIT, PoliceUnitType.HOMICIDE_UNIT, PoliceUnitType.NARCOTICS_UNIT, PoliceUnitType.VICE_UNIT ->
								addVehicle(new Vehicle(PoliceVehicleType.SEDAN, unit));
						case PoliceUnitType.DETECTIVE_UNIT -> addVehicle(new Vehicle(PoliceVehicleType.SUV, unit));
                        case MedicUnitType.BLS_UNIT -> addVehicle(new Vehicle(MedicVehicleType.BLS_AMBULANCE, unit));
						case MedicUnitType.ALS_UNIT -> addVehicle(new Vehicle(MedicVehicleType.ALS_AMBULANCE, unit));
                        default -> {}
                    }
				}
			}
		}

	}

}