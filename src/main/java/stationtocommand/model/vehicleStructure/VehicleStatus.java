package stationtocommand.model.vehicleStructure;

import javafx.scene.image.Image;
import stationtocommand.model.utilsStructure.EnumWithResource;

import java.util.Objects;

public enum VehicleStatus implements EnumWithResource {
    AVAILABLE("Available", "/images/status/availableStatus.png"),
    DISPATCHED("Dispatched", "/images/status/dispatchedStatus.png"),
    ON_SCENE("On Scene", "/images/status/onSceneStatus.png"),
    RETURNING("Returning", "/images/status/returningStatus.png"),
    UNAVAILABLE("Unavailable", "/images/status/unavailableStatus.png");

    private final String name;
    private final Image image;

    VehicleStatus(String name, String resourcePath) {
        this.name = name;
        this.image = new Image(Objects.requireNonNull(getClass().getResource(resourcePath)).toExternalForm());
    }

    @Override
    public String toString() {
        return this.name;
    }

    public Image getImage() {
        return Objects.requireNonNullElse(image, new Image("/images/blank.png"));
    }

    @Override
    public VehicleStatus[] getValues() {
        return values();
    }

    @Override
    public VehicleStatus getPrimaryValue() {
        return values()[0];
    }

}
