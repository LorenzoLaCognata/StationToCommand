package stationtocommand.model.unitTypeStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum FireUnitType implements UnitType {
  FIRE_ENGINE("Fire Engine", "/images/unit/fireEngine.png"),
  FIRE_TRUCK("Fire Truck", "/images/unit/fireTruck.png"),
  RESCUE_SQUAD("Rescue Squad", "/images/unit/rescueSquad.png");

  private final String name;
  private final Image image;

  FireUnitType(String name, String resourcePath) {
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
  public FireUnitType[] getValues() {
    return values();
  }

  @Override
  public FireUnitType getPrimaryValue() {
    return values()[0];
  }

}