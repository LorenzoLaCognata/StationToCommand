package model.departmentStructure;

public enum DepartmentType {

  FIRE_DEPARTMENT("Fire"),
  POLICE_DEPARTMENT("Police"),
  MEDIC_DEPARTMENT("EMS");

  private final String name;

  DepartmentType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}