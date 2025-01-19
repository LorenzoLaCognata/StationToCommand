package trainingStructure.trainingModule;

public enum TrainingType {
  FIRE_SAFETY("Fire Safety"),
  FIRE_SCIENCE("Fire Science"),
  FIRST_AID("First Aid"),
  CPR("CPR"),
  TRAUMA_CARE("Trauma Care");

  private final String name;

  TrainingType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}