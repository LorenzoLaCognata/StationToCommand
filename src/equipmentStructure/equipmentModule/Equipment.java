package equipmentStructure.equipmentModule;

import equipmentStructure.equipmentLinkModule.EquipmentUnitLink;
import linkStructure.organizationLinkModule.UnitLink;
import unitStructure.unitModule.Unit;

public class Equipment {

	private final EquipmentType equipmentType;
	private float integrity = 1.0f;
	private float condition = 1.0f;
	private UnitLink unitLink;

	public Equipment(EquipmentType equipmentType, Unit unit) {
		this.equipmentType = equipmentType;
		this.unitLink = new EquipmentUnitLink(unit);
		unit.linkEquipment(this);
	}

	@Override
	public String toString() {
		return equipmentType.toString();
	}

	public EquipmentType getEquipmentType() {
		return equipmentType;
	}

	public UnitLink getUnitLink() {
		return unitLink;
	}

	public float getIntegrity() {
		return integrity;
	}

	public void setIntegrity(float integrity) {
		this.integrity = integrity;
	}

	public float getCondition() {
		return condition;
	}

	public void setCondition(float condition) {
		this.condition = condition;
	}

	public void linkUnit(Unit unit) {
		this.unitLink = new EquipmentUnitLink(unit);
		unit.linkEquipment(this);
	}

}