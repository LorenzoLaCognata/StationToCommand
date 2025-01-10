package linkStructure.organizationLinkModule;

import unitStructure.unitModule.Unit;

public abstract class UnitLink {

    private final Unit unit;

    public UnitLink(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

}