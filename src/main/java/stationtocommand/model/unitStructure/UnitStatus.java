package stationtocommand.model.unitStructure;

public enum UnitStatus {
    AVAILABLE("Available"),
    DISPATCHED("Dispatched"),
    ON_SCENE("On Scene"),
    RETURNING("Returning"),
    UNAVAILABLE("Unavailable");

    private final String name;

    UnitStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
