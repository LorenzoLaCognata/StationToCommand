package equipmentStructure.equipmentModule;

import equipmentStructure.equipmentLinkModule.EquipmentUnitLink;
import linkStructure.organizationLinkModule.UnitLink;
import unitStructure.unitModule.Unit;

public class Equipment {

	private final UnitLink unitLink;

	public Equipment(Unit unit) {
		System.out.println("Equipment initializing");
		this.unitLink = new EquipmentUnitLink(unit);
		System.out.println("Equipment initialized successfully");
	}

	public void linkUnit(Unit unit) {
		// TODO
	}

}