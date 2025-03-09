package stationtocommand.model.stationStructure;

import java.util.Objects;

public enum StationType {

  FIRE_STATION("Fire", "/images/station/fireStation.png"),
  POLICE_STATION("Police","/images/station/policeStation.png"),
  MEDIC_STATION("EMS", "/images/station/medicStation.png");

  private final String name;
  private final String resourcePath;

  StationType(String name, String resourcePath) {
    this.name = name;
    this.resourcePath = resourcePath;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public String getResourcePath() {
    return Objects.requireNonNullElse(resourcePath, "/images/blank.png");
  }

}