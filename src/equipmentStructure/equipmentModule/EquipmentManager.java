package equipmentStructure.equipmentModule;

import departmentStructure.departmentModule.DepartmentManager;
import unitStructure.unitModule.FireUnit;
import unitStructure.unitTypeModule.FireUnitType;

import java.util.ArrayList;
import java.util.List;

public class EquipmentManager {

    private List<Equipment> equipments = new ArrayList<>();

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
		FireUnit unit = new FireUnit(FireUnitType.FIRE_ENGINE, 0);
		addEquipment(new Equipment(unit));

	}

}