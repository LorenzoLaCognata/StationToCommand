package model.unitStructure.unitTypeModule;

public enum FireUnitType implements UnitType {
  FIRE_ENGINE("Fire Engine"),
  FIRE_TRUCK("Fire Truck"),
  RESCUE_SQUAD("Rescue Squad");

  private final String name;

  FireUnitType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}