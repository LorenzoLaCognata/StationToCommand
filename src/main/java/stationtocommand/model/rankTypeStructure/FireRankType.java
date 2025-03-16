package stationtocommand.model.rankTypeStructure;

import java.util.Objects;

public enum FireRankType implements RankType {
  CANDIDATE_FIREFIGHTER("Candidate Firefighter", 1, null),
  FIREFIGHTER("Firefighter", 2, null),
  LIEUTENANT("Lieutenant", 3, null),
  CAPTAIN("Captain", 4, null),
  BATTALION_CHIEF("Battalion Chief", 5, null),
  DISTRICT_CHIEF("District Chief", 6, null),
  COMMISSIONER("Commissioner", 7, null);

  private final String name;
  private final int level;
  private final String resourcePath;

  FireRankType(String name, int level, String resourcePath) {
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