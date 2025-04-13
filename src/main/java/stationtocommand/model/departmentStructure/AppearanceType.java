package stationtocommand.model.departmentStructure;

import stationtocommand.model.utilsStructure.EnumWithResource;

import java.util.Objects;

public enum AppearanceType implements EnumWithResource {

  MALE_01("Male 01", "/images/responder/male01.png"),
  MALE_02("Male 01", "/images/responder/male02.png"),
  MALE_03("Male 01", "/images/responder/male03.png"),
  MALE_04("Male 01", "/images/responder/male04.png"),
  MALE_05("Male 01", "/images/responder/male05.png"),
  MALE_06("Male 01", "/images/responder/male06.png"),
  MALE_07("Male 01", "/images/responder/male07.png"),
  MALE_08("Male 01", "/images/responder/male08.png"),
  MALE_09("Male 01", "/images/responder/male09.png"),
  MALE_10("Male 01", "/images/responder/male10.png"),
  FEMALE_01("Female 01", "/images/responder/female01.png"),
  FEMALE_02("Female 01", "/images/responder/female02.png"),
  FEMALE_03("Female 01", "/images/responder/female03.png"),
  FEMALE_04("Female 01", "/images/responder/female04.png"),
  FEMALE_05("Female 01", "/images/responder/female05.png"),
  FEMALE_06("Female 01", "/images/responder/female06.png"),
  FEMALE_07("Female 01", "/images/responder/female07.png"),
  FEMALE_08("Female 01", "/images/responder/female08.png"),
  FEMALE_09("Female 01", "/images/responder/female09.png"),
  FEMALE_10("Female 01", "/images/responder/female10.png");

  private final String name;
  private final String resourcePath;

  AppearanceType(String name, String resourcePath) {
    this.name = name;
    this.resourcePath = resourcePath;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public String getResourcePath() {
    return Objects.requireNonNullElse(resourcePath, "/images/blank.png");
  }

  @Override
  public AppearanceType[] getValues() {
    return values();
  }

  @Override
  public AppearanceType getPrimaryValue() {
    return values()[0];
  }

}