package stationtocommand.model.vehicleStructure;

import java.util.Objects;

public enum PoliceVehicleType implements VehicleType {
  SEDAN("Police Sedan", "/images/vehicle/sedan.png"),
  SUV("Police SUV", "/images/vehicle/suv.png"),
  MOTORCYCLE("Police Motorcycle", "/images/vehicle/motorcycle.png");

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