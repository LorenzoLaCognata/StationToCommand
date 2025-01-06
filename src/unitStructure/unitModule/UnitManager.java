package unitStructure.unitModule;

import departmentStructure.departmentModule.DepartmentType;
import stationStructure.stationModule.StationManager;
import unitStructure.unitTypeModule.FireUnitType;
import unitStructure.unitTypeModule.MedicUnitType;
import unitStructure.unitTypeModule.PoliceUnitType;
import unitStructure.unitTypeModule.UnitType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnitManager {

	private final List<Unit> units = new ArrayList<>();

    public UnitManager(DepartmentType departmentType, StationManager stationManager) {
		initUnits(departmentType, stationManager);
    }

	@Override
	public String toString() {
		return units.stream()
				.map(unit -> "\t\t- " + unit)
				.collect(Collectors.joining("\n"));
	}

	public Unit getUnit(int number) {
		return units.stream()
				.filter(item -> item.getNumber() == number)
				.findFirst()
				.orElse(null);
	}

	public Unit getUnit(UnitType unitType) {
		return units.stream()
				.filter(item -> item.getUnitType().equals(unitType))
				.findFirst()
				.orElse(null);
	}

	public void addUnit(DepartmentType departmentType, UnitType unitType, StationManager stationManager) {
		if (departmentType == DepartmentType.FIRE_DEPARTMENT) {
			this.units.add(new FireUnit(unitType, stationManager.getNextUnitNumber()));
		}
		else if (departmentType == DepartmentType.POLICE_DEPARTMENT) {
			this.units.add(new PoliceUnit(unitType, stationManager.getNextUnitNumber()));
		}
		else if (departmentType == DepartmentType.MEDIC_DEPARTMENT) {
			this.units.add(new MedicUnit(unitType, stationManager.getNextUnitNumber()));
		}
		stationManager.setNextUnitNumber(stationManager.getNextUnitNumber()+1);
	}

	public void initUnits(DepartmentType departmentType, StationManager stationManager) {
		if (departmentType == DepartmentType.FIRE_DEPARTMENT) {
			addUnit(departmentType, FireUnitType.FIRE_ENGINE, stationManager);
			addUnit(departmentType, FireUnitType.FIRE_TRUCK, stationManager);
			addUnit(departmentType, FireUnitType.RESCUE_SQUAD, stationManager);
		}
		else if (departmentType == DepartmentType.POLICE_DEPARTMENT) {
			addUnit(departmentType, PoliceUnitType.PATROL_UNIT, stationManager);
			addUnit(departmentType, PoliceUnitType.DETECTIVE_UNIT, stationManager);
			addUnit(departmentType, PoliceUnitType.HOMICIDE_UNIT, stationManager);
		}
		else if (departmentType == DepartmentType.MEDIC_DEPARTMENT) {
			addUnit(departmentType, MedicUnitType.BLS_UNIT, stationManager);
			addUnit(departmentType, MedicUnitType.ALS_UNIT, stationManager);
		}
	}

}