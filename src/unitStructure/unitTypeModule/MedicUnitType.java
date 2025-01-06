package unitStructure.unitTypeModule;

public enum MedicUnitType implements UnitType {
  BLS_UNIT("Basic Life Support"),
  ALS_UNIT("Advanced Life Support");

  private final String name;

  MedicUnitType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}