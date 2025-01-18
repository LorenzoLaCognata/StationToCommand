package vehicleStructure.vehicleModule;

public enum PoliceVehicleType implements VehicleType {
  SEDAN("Police Sedan"),
  SUV("Police SUV"),
  MOTORCYCLE("Police Motorcycle");

  private final String name;

  PoliceVehicleType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}