package stationtocommand.model.unitStructure;

import stationtocommand.model.utilsStructure.EnumWithResource;

import java.util.Objects;

public enum UnitStatus implements EnumWithResource {
    AVAILABLE("Available", "/images/status/availableStatus.png"),
    DISPATCHED("Dispatched", "/images/status/dispatchedStatus.png"),
    ON_SCENE("On Scene", "/images/status/onSceneStatus.png"),
    RETURNING("Returning", "/images/status/returningStatus.png"),
    UNAVAILABLE("Unavailable", "/images/status/unavailableStatus.png");

    private final String name;
    private final String resourcePath;

    UnitStatus(String name, String resourcePath) {
        this.name = name;
        this.resourcePath = resourcePath;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public String getResourcePath() {
        return Objects.requireNonNullElse(resourcePath, "/images/blank.png");
    }

    @Override
    public UnitStatus[] getValues() {
        return values();
    }

    @Override
    public UnitStatus getPrimaryValue() {
        return values()[0];
    }

}
