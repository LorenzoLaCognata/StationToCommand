package stationtocommand.model.vehicleStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum MedicVehicleType implements VehicleType {
    BLS_AMBULANCE("BLS Ambulance", "/images/vehicle/blsAmbulance.png"),
    ALS_AMBULANCE("ALS Ambulance", "/images/vehicle/alsAmbulance.png");
  
    private final String name;
    private final Image image;

    MedicVehicleType(String name, String resourcePath) {
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
    public MedicVehicleType[] getValues() {
        return values();
    }
    
    @Override
    public MedicVehicleType getPrimaryValue() {
        return values()[0];
    }

}