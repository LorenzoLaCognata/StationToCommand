package equipmentStructure.equipmentModule;

import departmentStructure.departmentModule.Department;
import departmentStructure.departmentModule.DepartmentManager;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.FireUnit;
import unitStructure.unitTypeModule.FireUnitType;

import java.util.ArrayList;
import java.util.List;

public class EquipmentManager {

    private final List<Equipment> equipments = new ArrayList<>();

    public EquipmentManager(DepartmentManager departmentManager) {
      System.out.println("EquipmentManager initializing");
			initEquipments(departmentManager);
      System.out.println("EquipmentManager initialized successfully");
    }
	
	public Equipment getEquipment() {
		// TODO
		return equipments.get(0);
	}

	public void addEquipment(Equipment Equipment) {
		this.equipments.add(Equipment);
	}

	public void initEquipments(DepartmentManager departmentManager) {

		// TODO: loop through Stations and Units
		Department department = departmentManager.getDepartments().getFirst();
		Station station = department.getStations().getFirst();
		FireUnit unit = new FireUnit(station, FireUnitType.FIRE_ENGINE, 0);
		addEquipment(new Equipment(unit));

	}

}