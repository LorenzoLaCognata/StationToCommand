package model.unitTypeStructure;

public enum PoliceUnitType implements UnitType {
  PATROL_UNIT("Patrol"),
  DETECTIVE_UNIT("Detective"),
  HOMICIDE_UNIT("Homicide"),
  NARCOTICS_UNIT("Narcotics"),
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