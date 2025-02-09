package stationtocommand.model.skillStructure;

public enum SkillType {
  SELF_DEFENSE("Self-Defense"),
  WEAPON_HANDLING("Weapon Handling"),
  INTUBATION("Intubation"),
  IV_INSERTION("IV Insertion");

  private final String name;

  SkillType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}