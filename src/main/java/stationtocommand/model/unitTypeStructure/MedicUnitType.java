package stationtocommand.model.unitTypeStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum MedicUnitType implements UnitType {
  PRIMARY_CARE_UNIT("Primary Care", "/images/unit/primaryCare.png"),
  CRITICAL_CARE_UNIT("Critical Care", "/images/unit/criticalCare.png");

  private final String name;
  private final Image image;

  MedicUnitType(String name, String resourcePath) {
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
  public MedicUnitType[] getValues() {
    return values();
  }

  @Override
  public MedicUnitType getPrimaryValue() {
    return values()[0];
  }

}