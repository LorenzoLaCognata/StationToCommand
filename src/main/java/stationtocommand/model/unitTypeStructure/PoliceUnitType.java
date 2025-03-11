package stationtocommand.model.unitTypeStructure;

import java.util.Objects;

public enum PoliceUnitType implements UnitType {
  PATROL_UNIT("Patrol", "/images/unit/patrolUnit.png"),
  DETECTIVE_UNIT("Detective", "/images/unit/detectiveUnit.png"),
  HOMICIDE_UNIT("Homicide", "/images/unit/homicideUnit.png"),
  NARCOTICS_UNIT("Narcotics", "/images/unit/narcoticsUnit.png"),
  VICE_UNIT("Vice", "/images/unit/viceUnit.png");

  private final String name;
  private final String resourcePath;

  PoliceUnitType(String name, String resourcePath) {
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