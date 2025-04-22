package stationtocommand.model.vehicleStructure;

import java.util.Objects;

public enum PoliceVehicleType implements VehicleType {
  SEDAN("Police Sedan", "/images/vehicle/policeSedan.png"),
  PATROL_SEDAN("Patrol Sedan", "/images/vehicle/patrolSedan.png"),
  SUV("Police SUV", "/images/vehicle/policeSUV.png"),
  TACTICAL_SUV("Tactical SUV", "/images/vehicle/tacticalSUV.png"),
  MOTORCYCLE("Police Motorcycle", "/images/vehicle/policeMotorcycle.png");

  private final String name;
  private final String resourcePath;

  PoliceVehicleType(String name, String resourcePath) {
    this.name = name;
    this.resourcePath = resourcePath;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public String getResourcePath() {
    return Objects.requireNonNullElse(resourcePath, "/images/blank.png");
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