package stationtocommand.model.personStructure;

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