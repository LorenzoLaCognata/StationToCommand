package stationtocommand.model.vehicleStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentManager;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.utilsStructure.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleManager {

	public static final float PUMPER_RATIO = 1.00f;
	public static final float TANKER_RATIO = 0.30f;
	public static final float LADDER_RATIO = 1.00f;
	public static final float TOWER_RATIO = 0.30f;
	public static final float RESCUE_RATIO = 1.00f;
	public static final float HEAVY_RESCUE_RATIO = 0.50f;
	public static final float SEDAN_RATIO = 1.00f;
	public static final float SUV_RATIO = 1.00f;
	public static final float MOTORCYCLE_RATIO = 0.30f;
	public static final float BLS_AMBULANCE_RATIO = 1.00f;
	public static final float ALS_AMBULANCE_RATIO = 1.00f;

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
						case FireUnitType.FIRE_ENGINE -> {
							int pumperCount = Utils.randomIntegerFromRatio(PUMPER_RATIO);
							for (int i = 0; i< pumperCount; i++) {
								addVehicle(new Vehicle(FireVehicleType.PUMPER, unit));
							}
							int tankerCount = Utils.randomIntegerFromRatio(TANKER_RATIO);
							for (int i = 0; i< tankerCount; i++) {
								addVehicle(new Vehicle(FireVehicleType.TANKER, unit));
							}
						}
						case FireUnitType.FIRE_TRUCK -> {
							int ladderCount = Utils.randomIntegerFromRatio(LADDER_RATIO);
							for (int i = 0; i< ladderCount; i++) {
								addVehicle(new Vehicle(FireVehicleType.LADDER, unit));
							}
							int towerCount = Utils.randomIntegerFromRatio(TOWER_RATIO);
							for (int i = 0; i< towerCount; i++) {
								addVehicle(new Vehicle(FireVehicleType.TOWER, unit));
							}
						}
                        case FireUnitType.RESCUE_SQUAD -> {
							int rescueCount = Utils.randomIntegerFromRatio(RESCUE_RATIO);
							for (int i = 0; i< rescueCount; i++) {
								addVehicle(new Vehicle(FireVehicleType.RESCUE, unit));
							}
							int heavyRescueCount = Utils.randomIntegerFromRatio(HEAVY_RESCUE_RATIO);
							for (int i = 0; i< heavyRescueCount; i++) {
								addVehicle(new Vehicle(FireVehicleType.HEAVY_RESCUE, unit));
							}
						}
						case PoliceUnitType.PATROL_UNIT -> {
							int sedanCount = Utils.randomIntegerFromRatio(SEDAN_RATIO);
							for (int i = 0; i< sedanCount; i++) {
								addVehicle(new Vehicle(PoliceVehicleType.SEDAN, unit));
							}
							int motorcycleCount = Utils.randomIntegerFromRatio(MOTORCYCLE_RATIO);
							for (int i = 0; i< motorcycleCount; i++) {
								addVehicle(new Vehicle(PoliceVehicleType.MOTORCYCLE, unit));
								addVehicle(new Vehicle(PoliceVehicleType.MOTORCYCLE, unit));
							}
						}
						case PoliceUnitType.HOMICIDE_UNIT, PoliceUnitType.NARCOTICS_UNIT, PoliceUnitType.VICE_UNIT -> {
							int sedanCount = Utils.randomIntegerFromRatio(SEDAN_RATIO);
							for (int i = 0; i< sedanCount; i++) {
								addVehicle(new Vehicle(PoliceVehicleType.SEDAN, unit));
							}
						}
						case PoliceUnitType.DETECTIVE_UNIT -> {
							int suvCount = Utils.randomIntegerFromRatio(SUV_RATIO);
							for (int i = 0; i< suvCount; i++) {
								addVehicle(new Vehicle(PoliceVehicleType.SUV, unit));
							}
						}
                        case MedicUnitType.PRIMARY_CARE_UNIT -> {
							int blsAmbulanceCount = Utils.randomIntegerFromRatio(BLS_AMBULANCE_RATIO);
							for (int i = 0; i< blsAmbulanceCount; i++) {
								addVehicle(new Vehicle(MedicVehicleType.BLS_AMBULANCE, unit));
							}
						}
						case MedicUnitType.CRITICAL_CARE_UNIT -> {
							int alsAmbulanceCount = Utils.randomIntegerFromRatio(ALS_AMBULANCE_RATIO);
							for (int i = 0; i< alsAmbulanceCount; i++) {
								addVehicle(new Vehicle(MedicVehicleType.ALS_AMBULANCE, unit));
							}
						}
                        default -> {}
                    }
				}
			}
		}

	}

}