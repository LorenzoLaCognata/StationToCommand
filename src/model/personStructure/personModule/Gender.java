package model.personStructure.personModule;

public enum Gender {
  MALE("♂"),
  FEMALE("♀");

  private final String name;

  Gender(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}