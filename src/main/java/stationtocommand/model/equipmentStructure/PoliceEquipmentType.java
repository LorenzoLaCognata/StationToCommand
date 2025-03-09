package stationtocommand.model.equipmentStructure;

import java.util.Objects;

public enum PoliceEquipmentType implements EquipmentType {
  // TODO: replace with final icons
  BODY_ARMOR("Body Armor", null),
  HANDCUFFS("Handcuffs", null),
  FLASHLIGHT("Flashlight", null),
  BATON("Baton", null),
  TASER("Taser", null),
  EVIDENCE_KIT("Evidence Collection Kit", null);

  private final String name;
  private final String resourcePath;

  PoliceEquipmentType(String name, String resourcePath) {
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