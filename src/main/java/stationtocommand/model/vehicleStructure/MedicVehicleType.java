package stationtocommand.model.vehicleStructure;

import java.util.Objects;

public enum MedicVehicleType implements VehicleType {
  BLS_AMBULANCE("BLS Ambulance", "/images/vehicle/blsAmbulance.png"),
  ALS_AMBULANCE("ALS Ambulance", "/images/vehicle/alsAmbulance.png");

  private final String name;
  private final String resourcePath;

  MedicVehicleType(String name, String resourcePath) {
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

}