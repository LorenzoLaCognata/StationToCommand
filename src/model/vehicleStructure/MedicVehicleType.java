package model.vehicleStructure;

public enum MedicVehicleType implements VehicleType {
  BLS_AMBULANCE("BLS Ambulance"),
  ALS_AMBULANCE("ALS Ambulance");

  private final String name;

  MedicVehicleType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}