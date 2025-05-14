package stationtocommand.model.equipmentStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum MedicEquipmentType implements EquipmentType {
  // TODO: replace with final icons
  FIRST_AID_KIT("First Aid Kit", "/images/blank.png"),
  OXYGEN_MASK("Oxygen Mask", "/images/blank.png"),
  TRAUMA_BAG("Trauma Bag", "/images/blank.png"),
  CARDIAC_MONITOR("Cardiac Monitor", "/images/blank.png"),
  DEFIBRILLATOR("Defibrillator", "/images/blank.png"),
  INTUBATION_KIT("Intubation Kit", "/images/blank.png"),
  COLLAR("Collar", "/images/blank.png"),
  VENTILATOR("Portable Ventilator", "/images/blank.png"),
  BURN_KIT("Burn Kit", "/images/blank.png"),
  STRETCHER("Stretcher", "/images/blank.png");

  private final String name;
  private final Image image;

  MedicEquipmentType(String name, String resourcePath) {
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
  public MedicEquipmentType[] getValues() {
    return values();
  }

  @Override
  public MedicEquipmentType getPrimaryValue() {
    return values()[0];
  }

}