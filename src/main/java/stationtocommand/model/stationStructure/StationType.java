package stationtocommand.model.stationStructure;

import javafx.scene.image.Image;
import stationtocommand.model.utilsStructure.EnumWithResource;

import java.util.Objects;

public enum StationType implements EnumWithResource {

  FIRE_STATION("Fire", "/images/station/fireStation.png"),
  POLICE_STATION("Police","/images/station/policeStation.png"),
  MEDIC_STATION("EMS", "/images/station/medicStation.png");

  private final String name;
  private final Image image;

  StationType(String name, String resourcePath) {
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
  public StationType[] getValues() {
    return values();
  }

  @Override
  public StationType getPrimaryValue() {
    return values()[0];
  }

}