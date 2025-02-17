package stationtocommand.model.equipmentStructure;

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

public class EquipmentManager {

    private final List<Equipment> equipments = new ArrayList<>();

    public EquipmentManager(DepartmentManager departmentManager) {
		initEquipments(departmentManager);
    }

	@Override
	public String toString() {
		return equipments.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}

	public List<Equipment> getEquipments(EquipmentType equipmentType) {
		return equipments.stream()
				.filter(item -> item.getEquipmentType().equals(equipmentType))
				.toList();
	}

	public List<Equipment> getEquipments(Unit unit) {
		return equipments.stream()
				.filter(item -> item.getUnitLink().getUnit().equals(unit))
				.toList();
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
                        case MedicUnitType.PRIMARY_CARE_UNIT -> {
                            addEquipment(new Equipment(MedicEquipmentType.FIRST_AID_KIT, unit));
                            addEquipment(new Equipment(MedicEquipmentType.OXYGEN_MASK, unit));
                            addEquipment(new Equipment(MedicEquipmentType.STRETCHER, unit));
                        }
                        case MedicUnitType.CRITICAL_CARE_UNIT -> {
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