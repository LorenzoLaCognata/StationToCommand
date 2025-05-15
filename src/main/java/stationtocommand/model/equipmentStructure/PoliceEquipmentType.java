package stationtocommand.model.equipmentStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum PoliceEquipmentType implements EquipmentType {
  // TODO: replace with final icons
  BODY_ARMOR("Body Armor", "/images/blank.png"),
  HANDCUFFS("Handcuffs", "/images/blank.png"),
  FLASHLIGHT("Flashlight", "/images/blank.png"),
  BATON("Baton", "/images/blank.png"),
  TASER("Taser", "/images/blank.png"),
  EVIDENCE_KIT("Evidence Collection Kit", "/images/blank.png");

  private final String name;
  private final Image image;

  PoliceEquipmentType(String name, String resourcePath) {
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
  public PoliceEquipmentType[] getValues() {
    return values();
  }

  @Override
  public PoliceEquipmentType getPrimaryValue() {
    return values()[0];
  }

}