package unitStructure.unitModule;

import stationStructure.stationModule.Station;
import unitStructure.unitTypeModule.UnitType;

public abstract class Unit {

    private final Station station;
    private final UnitType unitType;
    private final int number;

    public Unit(Station station, UnitType unitType, int number) {
        this.station = station;
        this.unitType = unitType;
		this.number = number;
    }

    @Override
    public String toString() {
        return unitType + " Unit " + number;
    }

    public Station getStation() {
        return station;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public int getNumber() {
        return number;
    }
    
}