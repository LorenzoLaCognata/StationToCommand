package stationtocommand.model.rankTypeStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum FireRankType implements RankType {
  CANDIDATE_FIREFIGHTER("Candidate Firefighter", 1, "/images/rank/fireRank1.png"),
  FIREFIGHTER("Firefighter", 2, "/images/rank/fireRank2.png"),
  LIEUTENANT("Lieutenant", 3, "/images/rank/fireRank3.png"),
  CAPTAIN("Captain", 4, "/images/rank/fireRank4.png"),
  BATTALION_CHIEF("Battalion Chief", 5, "/images/rank/fireRank5.png"),
  DISTRICT_CHIEF("District Chief", 6, "/images/rank/fireRank6.png"),
  COMMISSIONER("Commissioner", 7, "/images/rank/fireRank7.png");

  private final String name;
  private final int level;
  private final Image image;

  FireRankType(String name, int level, String resourcePath) {
    this.name = name;
    this.level = level;
    this.image = new Image(Objects.requireNonNull(getClass().getResource(resourcePath)).toExternalForm());
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public int getLevel() {
    return this.level;
  }

  public Image getImage() {
    return Objects.requireNonNullElse(image, new Image("/images/blank.png"));
  }

  @Override
  public FireRankType[] getValues() {
    return values();
  }

  @Override
  public FireRankType getPrimaryValue() {
    return values()[0];
  }

}