package stationtocommand.model.vehicleStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentManager;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleManager {

    private final List<Vehicle> vehicles = new ArrayList<>();

    public VehicleManager(DepartmentManager departmentManager) {
			initVehicles(departmentManager);
    }

	@Override
	public String toString() {
		return vehicles.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public List<Vehicle> getVehicles(VehicleType vehicleType) {
		return vehicles.stream()
				.filter(item -> item.getVehicleType().equals(vehicleType))
				.toList();
	}

	public List<Vehicle> getVehicles(Unit unit) {
		return vehicles.stream()
				.filter(item -> item.getUnitLink().getUnit().equals(unit))
				.toList();
	}

	public List<Vehicle> getVehicles(Station station) {
		return vehicles.stream()
				.filter(item -> item.getUnitLink().getUnit().getStation().equals(station))
				.toList();
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
                        case MedicUnitType.PRIMARY_CARE_UNIT -> addVehicle(new Vehicle(MedicVehicleType.BLS_AMBULANCE, unit));
						case MedicUnitType.CRITICAL_CARE_UNIT -> addVehicle(new Vehicle(MedicVehicleType.ALS_AMBULANCE, unit));
                        default -> {}
                    }
				}
			}
		}

	}

}