package unitStructure.unitModule;

import unitStructure.unitTypeModule.UnitType;

public abstract class Unit {

    private final UnitType unitType;
    private final int number;

    public Unit(UnitType unitType, int number) {
        this.unitType = unitType;
		this.number = number;
    }

    @Override
    public String toString() {
        return "[UNIT] " + unitType + " Unit " + number;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public int getNumber() {
        return number;
    }
    
}