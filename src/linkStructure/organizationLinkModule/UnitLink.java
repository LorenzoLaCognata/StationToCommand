package linkStructure.organizationLinkModule;

import unitStructure.unitModule.Unit;

public abstract class UnitLink {

    private Unit unit;

    public UnitLink(Unit unit) {
        System.out.println("UnitLink initializing");
        this.unit = unit;
		System.out.println("UnitLink initialized successfully");
    }
	
}