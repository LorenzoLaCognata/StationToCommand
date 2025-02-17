package stationtocommand.model.unitTypeStructure;

public enum MedicUnitType implements UnitType {
  PRIMARY_CARE_UNIT("Primary Care"),
  CRITICAL_CARE_UNIT("Critical Care");

  private final String name;

  MedicUnitType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}