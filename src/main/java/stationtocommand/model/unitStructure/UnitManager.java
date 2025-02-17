package stationtocommand.model.unitStructure;

import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationManager;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnitManager {

	private final Station station;
	private final List<Unit> units = new ArrayList<>();

    public UnitManager(Station station, StationManager stationManager) {
		this.station = station;
		initUnits(station, stationManager);
    }

	@Override
	public String toString() {
		return units.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public List<Unit> getUnits() {
		return units;
	}

	public Unit getUnit(UnitType unitType, int number) {
		return units.stream()
				.filter(item -> item.getUnitType().equals(unitType) && item.getNumber() == number)
				.findAny()
				.orElse(null);
	}

	public List<Unit> getUnits(UnitType unitType) {
		return units.stream()
				.filter(item -> item.getUnitType().equals(unitType))
				.toList();
	}

	public void addUnit(DepartmentType departmentType, UnitType unitType, StationManager stationManager) {
		if (departmentType == DepartmentType.FIRE_DEPARTMENT) {
			this.units.add(new FireUnit(station, unitType, stationManager.nextUnitNumber()));
		}
		else if (departmentType == DepartmentType.POLICE_DEPARTMENT) {
			this.units.add(new PoliceUnit(station, unitType, stationManager.nextUnitNumber()));
		}
		else if (departmentType == DepartmentType.MEDIC_DEPARTMENT) {
			this.units.add(new MedicUnit(station, unitType, stationManager.nextUnitNumber()));
		}
	}

	public void initUnits(Station station, StationManager stationManager) {
		DepartmentType departmentType = station.getDepartment().getDepartmentType();
		if (departmentType.equals(DepartmentType.FIRE_DEPARTMENT)) {
			addUnit(departmentType, FireUnitType.FIRE_ENGINE, stationManager);
			addUnit(departmentType, FireUnitType.FIRE_TRUCK, stationManager);
			addUnit(departmentType, FireUnitType.RESCUE_SQUAD, stationManager);
		}
		else if (departmentType.equals(DepartmentType.POLICE_DEPARTMENT)) {
			addUnit(departmentType, PoliceUnitType.PATROL_UNIT, stationManager);
			addUnit(departmentType, PoliceUnitType.DETECTIVE_UNIT, stationManager);
			addUnit(departmentType, PoliceUnitType.HOMICIDE_UNIT, stationManager);
		}
		else if (departmentType.equals(DepartmentType.MEDIC_DEPARTMENT)) {
			addUnit(departmentType, MedicUnitType.PRIMARY_CARE_UNIT, stationManager);
			addUnit(departmentType, MedicUnitType.CRITICAL_CARE_UNIT, stationManager);
		}
	}

}