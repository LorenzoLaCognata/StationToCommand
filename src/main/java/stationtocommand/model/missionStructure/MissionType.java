package stationtocommand.model.missionStructure;

import java.util.Objects;

public enum MissionType {
  // TODO: replace with final probability
  STRUCTURE_FIRE("Structure Fire", "/images/mission/structureFire.png", 1.0f),
  // TODO: replace with final icons
  VEHICLE_FIRE("Vehicle Fire", "/images/fireMission.png", 0.0f),
  WATER_RESCUE("Water Rescue", "/images/rescueMission.png", 0.0f),
  COLLAPSE_RESCUE("Collapse Rescue", "/images/rescueMission.png", 0.0f),
  TRAFFIC_INCIDENT("Traffic Incident", "/images/rescueMission.png", 0.0f),
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
  private final String resourcePath;
  private final float probability;

  MissionType(String name, String resourcePath, float probability) {
    this.name = name;
    this.resourcePath = resourcePath;
    this.probability = probability;
  }

  @Override
  public String toString() {
    return this.name;
  }

  public String getResourcePath() {
    return Objects.requireNonNullElse(resourcePath, "/images/blank.png");
  }

}