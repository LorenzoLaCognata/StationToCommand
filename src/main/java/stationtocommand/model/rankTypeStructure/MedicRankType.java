package stationtocommand.model.rankTypeStructure;

import java.util.Objects;

public enum MedicRankType implements RankType {
  EMR("Emergency Medical Responder", 1, null),
  EMT("Emergency Medical Technician", 2, null),
  PARAMEDIC("Paramedic", 3, null),
  PARAMEDIC_IN_CHARGE("Paramedic-in-Charge", 4, null),
  PARAMEDIC_SUPERVISOR("Paramedic Field Supervisor", 5, null),
  PARAMEDIC_FIELD_CHIEF("Paramedic Field Chief", 6, null),
  CHIEF_PARAMEDIC("Chief Paramedic", 7, null);

  private final String name;
  private final int level;
  private final String resourcePath;

  MedicRankType(String name, int level, String resourcePath) {
    this.name = name;
    this.level = level;
    this.resourcePath = resourcePath;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public int getLevel() {
    return this.level;
  }

  @Override
  public String getResourcePath() {
    return Objects.requireNonNullElse(resourcePath, "/images/blank.png");
  }

}