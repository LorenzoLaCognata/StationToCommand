package equipmentStructure.equipmentModule;

public enum MedicEquipmentType implements EquipmentType {
  FIRST_AID_KIT("First Aid Kit"),
  OXYGEN_MASK("Oxygen Mask"),
  TRAUMA_BAG("Trauma Bag"),
  CARDIAC_MONITOR("Cardiac Monitor"),
  DEFIBRILLATOR("Defibrillator"),
  INTUBATION_KIT("Intubation Kit"),
  COLLAR("Collar"),
  VENTILATOR("Portable Ventilator"),
  BURN_KIT("Burn Kit"),
  STRETCHER("Stretcher");

  private final String name;

  MedicEquipmentType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}