package stationtocommand.model.rankTypeStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum PoliceRankType implements RankType {
  OFFICER("Officer", 1, "/images/rank/policeRank1.png"),
  SERGEANT("Sergeant", 2, "/images/rank/policeRank2.png"),
  LIEUTENANT("Lieutenant", 3, "/images/rank/policeRank3.png"),
  CAPTAIN("Captain", 4, "/images/rank/policeRank4.png"),
  COMMANDER("Commander", 5, "/images/rank/policeRank5.png"),
  CHIEF("Chief", 6, "/images/rank/policeRank6.png"),
  SUPERINTENDENT("Superintendent", 7, "/images/rank/policeRank7.png");

  private final String name;
  private final int level;
  private final Image image;

  PoliceRankType(String name, int level, String resourcePath) {
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
  public PoliceRankType[] getValues() {
    return values();
  }

  @Override
  public PoliceRankType getPrimaryValue() {
    return values()[0];
  }

}