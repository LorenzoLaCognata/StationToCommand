package stationtocommand.model.rankTypeStructure;

import javafx.scene.image.Image;

import java.util.Objects;

public enum MedicRankType implements RankType {
  EMR("Emergency Medical Responder", 1, "/images/rank/medicRank1.png"),
  EMT("Emergency Medical Technician", 2, "/images/rank/medicRank2.png"),
  PARAMEDIC("Paramedic", 3, "/images/rank/medicRank3.png"),
  PARAMEDIC_IN_CHARGE("Paramedic-in-Charge", 4, "/images/rank/medicRank4.png"),
  PARAMEDIC_SUPERVISOR("Paramedic Field Supervisor", 5, "/images/rank/medicRank5.png"),
  PARAMEDIC_FIELD_CHIEF("Paramedic Field Chief", 6, "/images/rank/medicRank6.png"),
  CHIEF_PARAMEDIC("Chief Paramedic", 7, "/images/rank/medicRank7.png");

  private final String name;
  private final int level;
  private final Image image;

  MedicRankType(String name, int level, String resourcePath) {
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
  public MedicRankType[] getValues() {
    return values();
  }

  @Override
  public MedicRankType getPrimaryValue() {
    return values()[0];
  }

}