package stationtocommand.model.rankTypeStructure;

import java.util.Objects;

public enum PoliceRankType implements RankType {
  OFFICER("Officer", 1, null),
  SERGEANT("Sergeant", 2, null),
  LIEUTENANT("Lieutenant", 3, null),
  CAPTAIN("Captain", 4, null),
  COMMANDER("Commander", 5, null),
  CHIEF("Chief", 6, null),
  SUPERINTENDENT("Superintendent", 7, null);

  private final String name;
  private final int level;
  private final String resourcePath;

  PoliceRankType(String name, int level, String resourcePath) {
    this.name = name;
    this.level = level;
    this.resourcePath = resourcePath;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public int getLevel() {
    return this.level;
  }

  @Override
  public String getResourcePath() {
    return Objects.requireNonNullElse(resourcePath, "/images/blank.png");
  }

}