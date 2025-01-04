package unitStructure.unitModule;

import departmentStructure.departmentModule.DepartmentType;
import unitStructure.unitTypeModule.FireUnitType;
import unitStructure.unitTypeModule.MedicUnitType;
import unitStructure.unitTypeModule.PoliceUnitType;
import unitStructure.unitTypeModule.UnitType;

import java.util.ArrayList;
import java.util.List;

public class UnitManager {

	private List<Unit> units = new ArrayList<>();

    public UnitManager(DepartmentType departmentType) {
    	System.out.println("UnitManager initializing");
			initUnits(departmentType);
      System.out.println("UnitManager initialized successfully");
    }

	public Unit getUnit(int number) {
		// TODO
		return units.get(0);
	}

	public Unit getUnit(UnitType unitType) {
		// TODO
		return units.get(0);
	}

	public void addUnit(Unit unit) {
		this.units.add(unit);
	}

	public void initUnits(DepartmentType departmentType) {
		if (departmentType == DepartmentType.FireDepartment) {
			FireUnit unit = new FireUnit(FireUnitType.FireEngine);
			addUnit(unit);
		}
		else if (departmentType == DepartmentType.PoliceDepartment) {
			PoliceUnit unit = new PoliceUnit(PoliceUnitType.Patrol);
			addUnit(unit);
		}
		else if (departmentType == DepartmentType.MedicDepartment) {
			MedicUnit unit = new MedicUnit(MedicUnitType.BasicLifeSupport);
			addUnit(unit);
		}
	}

}