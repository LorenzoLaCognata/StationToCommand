package unitStructure.unitModule;

import unitStructure.unitTypeModule.UnitType;

public abstract class Unit {

    private UnitType unitType;
    private int number;

    public Unit(UnitType unitType) {
        System.out.println("Unit initializing");
        this.unitType = unitType;
		this.number = 1; // TODO: unit counter
        System.out.println("Unit initialized successfully");
    }

}