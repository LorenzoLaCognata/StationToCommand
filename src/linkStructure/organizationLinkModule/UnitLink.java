package linkStructure.organizationLinkModule;

import unitStructure.unitModule.Unit;

public abstract class UnitLink {

    private Unit unit;

    public UnitLink(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

}