package stationtocommand.model.unitTypeStructure;

import java.util.Objects;

public enum FireUnitType implements UnitType {
  FIRE_ENGINE("Fire Engine", "/images/unit/fireEngine.png"),
  FIRE_TRUCK("Fire Truck", "/images/unit/fireTruck.png"),
  RESCUE_SQUAD("Rescue Squad", "/images/unit/rescueSquad.png");

  private final String name;
  private final String resourcePath;

  FireUnitType(String name, String resourcePath) {
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