package stationtocommand.model.objectiveStructure;

public enum ObjectiveType {
  SECURE_AREA("Secure Area"),
  EVACUATE_CIVILIANS("Evacuate Civilians"),
  RESCUE_VICTIM("Rescue Victim"),
  NEUTRALIZE_THREAT("Neutralize Threat");

  private final String name;

  ObjectiveType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}