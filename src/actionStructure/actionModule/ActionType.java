package actionStructure.actionModule;

public enum ActionType {
  PERFORM_CPR("Perform CPR"),
  USE_DEFIBRILLATOR("Use Defibrillator"),
  SETUP_PERIMETER("Setup Perimeter"),
  SECURE_EVIDENCE("Secure Evidence");

  private final String name;

  ActionType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}