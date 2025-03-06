package stationtocommand.model.unitStructure;

import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationManager;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.utilsStructure.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UnitManager {

	public static final float FIRE_ENGINE_RATIO = 1.00f;
	public static final float FIRE_TRUCK_RATIO = 0.40f;
	public static final float RESCUE_SQUAD_RATIO = 0.25f;

	public static final float PATROL_RATIO = 20.0f;
	public static final float DETECTIVE_RATIO = 1.0f;
	public static final float HOMICIDE_RATIO = 0.4f;
	public static final float NARCOTICS_RATIO = 0.5f;
	public static final float VICE_RATIO = 0.5f;

	public static final float PRIMARY_CARE_RATIO = 0.8f;
	public static final float CRITICAL_CARE_RATIO = 1.0f;

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

	public List<Unit> getAvailableUnits(UnitType unitType) {
		return getUnits(unitType).stream()
				.filter(item -> item.getUnitStatus().equals(UnitStatus.AVAILABLE))
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
		switch (departmentType) {
			case FIRE_DEPARTMENT -> {
				randomUnits(stationManager, departmentType, FireUnitType.FIRE_ENGINE, FIRE_ENGINE_RATIO);
				randomUnits(stationManager, departmentType, FireUnitType.FIRE_TRUCK, FIRE_TRUCK_RATIO);
				randomUnits(stationManager, departmentType, FireUnitType.RESCUE_SQUAD, RESCUE_SQUAD_RATIO);
			}
			case POLICE_DEPARTMENT -> {
				randomUnits(stationManager, departmentType, PoliceUnitType.PATROL_UNIT, PATROL_RATIO);
				randomUnits(stationManager, departmentType, PoliceUnitType.DETECTIVE_UNIT, DETECTIVE_RATIO);
				randomUnits(stationManager, departmentType, PoliceUnitType.HOMICIDE_UNIT, HOMICIDE_RATIO);
				randomUnits(stationManager, departmentType, PoliceUnitType.NARCOTICS_UNIT, NARCOTICS_RATIO);
				randomUnits(stationManager, departmentType, PoliceUnitType.VICE_UNIT, VICE_RATIO);
			}
			case MEDIC_DEPARTMENT -> {
				randomUnits(stationManager, departmentType, MedicUnitType.PRIMARY_CARE_UNIT, PRIMARY_CARE_RATIO);
				randomUnits(stationManager, departmentType, MedicUnitType.CRITICAL_CARE_UNIT, CRITICAL_CARE_RATIO);
			}
		}
	}

	private void randomUnits(StationManager stationManager, DepartmentType departmentType, UnitType unitType, float unitRatio) {
		int unitCount = Utils.randomIntegerFromRatio(unitRatio);
		for (int i=0; i<unitCount; i++) {
			addUnit(departmentType, unitType, stationManager);
		}
	}

}