package stationtocommand.model.missionStructure;

import javafx.scene.image.Image;
import stationtocommand.model.utilsStructure.EnumWithResource;

import java.util.Objects;

public enum MissionType implements EnumWithResource {
  // TODO: replace with final probability
  STRUCTURE_FIRE("Structure Fire", "/images/mission/structureFire.png", 1.0f),
  VEHICLE_FIRE("Vehicle Fire", "/images/mission/vehicleFire.png", 0.0f),
  WATER_RESCUE("Water Rescue", "/images/mission/waterRescue.png", 0.0f),
  // TODO: replace with final icons
  COLLAPSE_RESCUE("Collapse Rescue", "/images/mission/collapseRescue.png", 0.0f),
  TRAFFIC_COLLISION("Traffic Collision", "/images/rescueMission.png", 0.0f),
  BURGLARY("Burglary", "/images/policeMission.png", 0.0f),
  ASSAULT("Assault", "/images/policeMission.png", 0.0f),
  DISTURBANCE("Disturbance", "/images/policeMission.png", 0.0f),
  HOMICIDE("Homicide", "/images/policeMission.png", 0.0f),
  DRUG_CRIME("Drug Crime", "/images/policeMission.png", 0.0f),
  VICE_CRIME("Vice Crime", "/images/policeMission.png", 0.0f),
  CROWD_CONTROL("Crowd Control", "/images/policeMission.png", 0.0f),
  MEDICAL_EMERGENCY("Medical Emergency", "/images/medicMission.png", 0.0f),
  TRAUMA_RESPONSE("Trauma Response", "/images/medicMission.png", 0.0f),
  CARDIAC_ARREST("Cardiac Arrest", "/images/medicMission.png", 0.0f),
  POISONING_OVERDOSE("Poisoning/Overdose", "/images/medicMission.png", 0.0f);

  private final String name;
  private final Image image;
  private final float probability;

  MissionType(String name, String resourcePath, float probability) {
    this.name = name;
    this.image = new Image(Objects.requireNonNull(getClass().getResource(resourcePath)).toExternalForm());
    this.probability = probability;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public Image getImage() {
    return Objects.requireNonNullElse(image, new Image("/images/blank.png"));
  }

  @Override
  public MissionType[] getValues() {
    return values();
  }

  @Override
  public MissionType getPrimaryValue() {
    return values()[0];
  }

}