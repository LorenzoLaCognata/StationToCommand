package model.equipmentStructure;

public enum PoliceEquipmentType implements EquipmentType {
  BODY_ARMOR("Body Armor"),
  HANDCUFFS("Handcuffs"),
  FLASHLIGHT("Flashlight"),
  BATON("Baton"),
  TASER("Taser"),
  EVIDENCE_KIT("Evidence Collection Kit");

  private final String name;

  PoliceEquipmentType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}