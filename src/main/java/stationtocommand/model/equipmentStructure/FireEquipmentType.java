package stationtocommand.model.equipmentStructure;

import java.util.Objects;

public enum FireEquipmentType implements EquipmentType {
  HOSE("Fire Hose", "/images/equipment/fireHose.png"),
  NOZZLE("Fog Nozzle", "/images/equipment/fogNozzle.png"),
  AXE("Fire Axe", "/images/equipment/fireAxe.png"),
  // TODO: replace with final icons
  HALLIGAN("Halligan Bar", null),
  LADDER("Portable Ladder", null),
  CUTTER("Hydraulic Cutter", null),
  SPREADER("Hydraulic Spreader", null),
  SCBA("Self-Contained Breathing Apparatus", null);

  private final String name;
  private final String resourcePath;

  FireEquipmentType(String name, String resourcePath) {
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