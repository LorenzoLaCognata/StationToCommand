package stationtocommand.model.departmentStructure;

import stationtocommand.model.utilsStructure.EnumWithResource;
import stationtocommand.model.vehicleStructure.FireVehicleType;
import stationtocommand.model.vehicleStructure.MedicVehicleType;
import stationtocommand.model.vehicleStructure.PoliceVehicleType;
import stationtocommand.model.vehicleStructure.VehicleType;

import java.util.Objects;

public enum DepartmentType implements EnumWithResource {

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

  @Override
  public DepartmentType[] getValues() {
    return values();
  }

  @Override
  public DepartmentType getPrimaryValue() {
    return values()[0];
  }

}