package stationtocommand.model.departmentStructure;

import java.util.Objects;

public enum DepartmentType {

  FIRE_DEPARTMENT("Fire", "/images/department/fireDepartment.png"),
  POLICE_DEPARTMENT("Police","/images/department/policeDepartment.png"),
  MEDIC_DEPARTMENT("EMS", "/images/department/medicDepartment.png");

  private final String name;
  private final String resourcePath;

  DepartmentType(String name, String resourcePath) {
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