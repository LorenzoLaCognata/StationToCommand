package stationtocommand.model.unitTypeStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum PoliceUnitType implements UnitType {
  PATROL_UNIT("Patrol", "/images/unit/patrolUnit.png"),
  DETECTIVE_UNIT("Detective", "/images/unit/detectiveUnit.png"),
  HOMICIDE_UNIT("Homicide", "/images/unit/homicideUnit.png"),
  NARCOTICS_UNIT("Narcotics", "/images/unit/narcoticsUnit.png"),
  VICE_UNIT("Vice", "/images/unit/viceUnit.png");

  private final String name;
  private final Image image;

  PoliceUnitType(String name, String resourcePath) {
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
  public PoliceUnitType[] getValues() {
    return values();
  }

  @Override
  public PoliceUnitType getPrimaryValue() {
    return values()[0];
  }

}