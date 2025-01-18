package equipmentStructure.equipmentModule;

import equipmentStructure.equipmentLinkModule.EquipmentUnitLink;
import linkStructure.organizationLinkModule.UnitLink;
import unitStructure.unitModule.Unit;

public class Equipment {

	private final EquipmentType equipmentType;
	private UnitLink unitLink;

	public Equipment(EquipmentType equipmentType, Unit unit) {
		this.equipmentType = equipmentType;
		this.unitLink = new EquipmentUnitLink(unit);
	}

	@Override
	public String toString() {
		return "[EQUIPMENT] " + this.equipmentType;
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public UnitLink getUnitLink() {
		return unitLink;
	}

	public void linkUnit(Unit unit) {
		this.unitLink = new EquipmentUnitLink(unit);
	}

}