package vehicleStructure.vehicleModule;

public enum FireVehicleType implements VehicleType {
  PUMPER("Pumper Fire Truck"),
  TANKER("Tanker Fire Truck"),

  LADDER("Aerial Ladder Truck"),
  TOWER("Tower Ladder Truck"),

  RESCUE("Rescue Truck"),
  HEAVY_RESCUE("Heavy Rescue Truck");

  private final String name;

  FireVehicleType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}