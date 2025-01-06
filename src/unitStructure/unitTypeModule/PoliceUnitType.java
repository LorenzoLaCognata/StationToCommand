package unitStructure.unitTypeModule;

public enum PoliceUnitType implements UnitType {
  PATROL_UNIT("Patrol"),
  DETECTIVE_UNIT("Detective"),
  NARCOTICS_UNIT("Narcotics"),
  HOMICIDE_UNIT("Homicide"),
  VICE_UNIT("Vice");

  private final String name;

  PoliceUnitType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}