package stationtocommand.model.vehicleStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum PoliceVehicleType implements VehicleType {
  SEDAN("Police Sedan", "/images/vehicle/policeSedan.png"),
  PATROL_SEDAN("Patrol Sedan", "/images/vehicle/patrolSedan.png"),
  SUV("Police SUV", "/images/vehicle/policeSUV.png"),
  TACTICAL_SUV("Tactical SUV", "/images/vehicle/tacticalSUV.png"),
  MOTORCYCLE("Police Motorcycle", "/images/vehicle/policeMotorcycle.png");

  private final String name;
  private final Image image;

  PoliceVehicleType(String name, String resourcePath) {
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
  public PoliceVehicleType[] getValues() {
    return values();
  }

  @Override
  public PoliceVehicleType getPrimaryValue() {
    return values()[0];
  }

}