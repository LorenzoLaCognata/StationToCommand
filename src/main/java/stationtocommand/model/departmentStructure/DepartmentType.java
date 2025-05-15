package stationtocommand.model.departmentStructure;

import javafx.scene.image.Image;
import stationtocommand.model.utilsStructure.EnumWithResource;

import java.util.Objects;

public enum DepartmentType implements EnumWithResource {

  FIRE_DEPARTMENT("Fire", "/images/department/fireDepartment.png"),
  POLICE_DEPARTMENT("Police","/images/department/policeDepartment.png"),
  MEDIC_DEPARTMENT("EMS", "/images/department/medicDepartment.png");

  private final String name;
  private final Image image;

  DepartmentType(String name, String resourcePath) {
    this.name = name;
    this.image = new Image(Objects.requireNonNull(getClass().getResource(resourcePath)).toExternalForm());
  }

  @Override
  public String toString() {
    return this.name;
  }

  public Image getImage() {
    return Objects.requireNonNullElse(image, new Image("/images/blank.png"));
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