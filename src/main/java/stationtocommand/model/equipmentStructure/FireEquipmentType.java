package stationtocommand.model.equipmentStructure;

public enum FireEquipmentType implements EquipmentType {
  HOSE("Fire Hose"),
  NOZZLE("Fog Nozzle"),

  AXE("Fire Axe"),
  HALLIGAN("Halligan Bar"),
  LADDER("Portable Ladder"),
  CUTTER("Hydraulic Cutter"),
  SPREADER("Hydraulic Spreader"),
  SCBA("Self-Contained Breathing Apparatus"),
  EXTINGUISHER("Fire Extinguisher");

  private final String name;

  FireEquipmentType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}