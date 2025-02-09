package stationtocommand.model.unitStructure;

public abstract class UnitLink {

    private final Unit unit;

    public UnitLink(Unit unit) {
        this.unit = unit;
    }

    public Unit getUnit() {
        return unit;
    }

}