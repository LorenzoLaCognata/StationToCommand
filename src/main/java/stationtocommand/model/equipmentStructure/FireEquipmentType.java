package stationtocommand.model.equipmentStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum FireEquipmentType implements EquipmentType {
  HOSE("Fire Hose", "/images/equipment/fireHose.png"),
  NOZZLE("Fog Nozzle", "/images/equipment/fogNozzle.png"),
  AXE("Fire Axe", "/images/equipment/fireAxe.png"),
  // TODO: replace with final icons
  HALLIGAN("Halligan Bar", "/images/blank.png"),
  LADDER("Portable Ladder", "/images/blank.png"),
  CUTTER("Hydraulic Cutter", "/images/blank.png"),
  SPREADER("Hydraulic Spreader", "/images/blank.png"),
  SCBA("Self-Contained Breathing Apparatus", "/images/blank.png");

  private final String name;
  private final Image image;

  FireEquipmentType(String name, String resourcePath) {
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
  public FireEquipmentType[] getValues() {
    return values();
  }

  @Override
  public FireEquipmentType getPrimaryValue() {
    return values()[0];
  }

}