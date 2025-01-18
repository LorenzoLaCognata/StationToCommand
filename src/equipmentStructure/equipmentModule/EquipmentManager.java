package equipmentStructure.equipmentModule;

import departmentStructure.departmentModule.Department;
import departmentStructure.departmentModule.DepartmentManager;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.Unit;
import unitStructure.unitTypeModule.FireUnitType;
import unitStructure.unitTypeModule.MedicUnitType;
import unitStructure.unitTypeModule.PoliceUnitType;

import java.util.ArrayList;
import java.util.List;

public class EquipmentManager {

    private final List<Equipment> equipments = new ArrayList<>();

    public EquipmentManager(DepartmentManager departmentManager) {
		initEquipments(departmentManager);
    }

	@Override
	public String toString() {
		return this.equipments.toString();
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public Equipment getEquipment(EquipmentType equipmentType) {
		return equipments.stream()
				.filter(item -> item.getEquipmentType().equals(equipmentType))
				.findFirst()
				.orElse(null);
	}

	public Equipment getEquipment(Unit unit) {
		return equipments.stream()
				.filter(item -> item.getUnitLink().getUnit().equals(unit))
				.findFirst()
				.orElse(null);
	}

	public void addEquipment(Equipment Equipment) {
		this.equipments.add(Equipment);
	}

	public void initEquipments(DepartmentManager departmentManager) {

		for (Department department: departmentManager.getDepartments()) {
			for (Station station : department.getStations()) {
				for (Unit unit : station.getUnits()) {
					switch (unit.getUnitType()) {
                        case FireUnitType.FIRE_ENGINE -> {
                            addEquipment(new Equipment(FireEquipmentType.HOSE, unit));
                            addEquipment(new Equipment(FireEquipmentType.NOZZLE, unit));
                        }
                        case FireUnitType.FIRE_TRUCK -> {
                            addEquipment(new Equipment(FireEquipmentType.HALLIGAN, unit));
                            addEquipment(new Equipment(FireEquipmentType.LADDER, unit));
                        }
                        case FireUnitType.RESCUE_SQUAD -> {
                            addEquipment(new Equipment(FireEquipmentType.CUTTER, unit));
                            addEquipment(new Equipment(FireEquipmentType.SPREADER, unit));
                        }
                        case PoliceUnitType.PATROL_UNIT, PoliceUnitType.HOMICIDE_UNIT, PoliceUnitType.NARCOTICS_UNIT, PoliceUnitType.VICE_UNIT -> {
                            addEquipment(new Equipment(PoliceEquipmentType.FLASHLIGHT, unit));
							addEquipment(new Equipment(PoliceEquipmentType.HANDCUFFS, unit));
							addEquipment(new Equipment(PoliceEquipmentType.BODY_ARMOR, unit));
                        }
                        case PoliceUnitType.DETECTIVE_UNIT -> {
							addEquipment(new Equipment(PoliceEquipmentType.FLASHLIGHT, unit));
							addEquipment(new Equipment(PoliceEquipmentType.HANDCUFFS, unit));
                            addEquipment(new Equipment(PoliceEquipmentType.EVIDENCE_KIT, unit));
                        }
                        case MedicUnitType.BLS_UNIT -> {
                            addEquipment(new Equipment(MedicEquipmentType.FIRST_AID_KIT, unit));
                            addEquipment(new Equipment(MedicEquipmentType.OXYGEN_MASK, unit));
                            addEquipment(new Equipment(MedicEquipmentType.STRETCHER, unit));
                        }
                        case MedicUnitType.ALS_UNIT -> {
                            addEquipment(new Equipment(MedicEquipmentType.INTUBATION_KIT, unit));
                            addEquipment(new Equipment(MedicEquipmentType.VENTILATOR, unit));
                            addEquipment(new Equipment(MedicEquipmentType.DEFIBRILLATOR, unit));
                        }
                        default -> {}
					}
				}
			}
		}

	}

}