package stationtocommand.model.unitTypeStructure;

import java.util.Objects;

public enum MedicUnitType implements UnitType {
  PRIMARY_CARE_UNIT("Primary Care", "/images/unit/primaryCare.png"),
  CRITICAL_CARE_UNIT("Critical Care", "/images/unit/criticalCare.png");

  private final String name;
  private final String resourcePath;

  MedicUnitType(String name, String resourcePath) {
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