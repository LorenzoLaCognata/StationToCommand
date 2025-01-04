package equipmentStructure.equipmentModule;

import unitStructure.unitModule.Unit;
import linkStructure.organizationLinkModule.UnitLink;

public class Equipment {

	private UnitLink unitLink;

	public Equipment(Unit unit) {
		System.out.println("Equipment initializing");
		linkUnit(unit);
		System.out.println("Equipment initialized successfully");
	}

	public void linkUnit(Unit unit) {
		// TODO
	}

}