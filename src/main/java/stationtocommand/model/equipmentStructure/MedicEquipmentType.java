package stationtocommand.model.equipmentStructure;

import java.util.Objects;

public enum MedicEquipmentType implements EquipmentType {
  // TODO: replace with final icons
  FIRST_AID_KIT("First Aid Kit", null),
  OXYGEN_MASK("Oxygen Mask", null),
  TRAUMA_BAG("Trauma Bag", null),
  CARDIAC_MONITOR("Cardiac Monitor", null),
  DEFIBRILLATOR("Defibrillator", null),
  INTUBATION_KIT("Intubation Kit", null),
  COLLAR("Collar", null),
  VENTILATOR("Portable Ventilator", null),
  BURN_KIT("Burn Kit", null),
  STRETCHER("Stretcher", null);

  private final String name;
  private final String resourcePath;

  MedicEquipmentType(String name, String resourcePath) {
    this.name = name;
    this.resourcePath = resourcePath;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public String getResourcePath() {
    return Objects.requireNonNullElse(resourcePath, "/images/blank.png");
  }

}