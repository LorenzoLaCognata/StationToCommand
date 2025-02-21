package stationtocommand.model.missionStructure;

public enum MissionType {
  STRUCTURE_FIRE("Structure Fire", 1.0f),
  VEHICLE_FIRE("Vehicle Fire", 0.0f),
  WATER_RESCUE("Water Rescue", 0.0f),
  COLLAPSE_RESCUE("Collapse Rescue", 0.0f),
  TRAFFIC_INCIDENT("Traffic Incident", 0.0f),
  BURGLARY("Burglary", 0.0f),
  ASSAULT("Assault", 0.0f),
  DISTURBANCE("Disturbance", 0.0f),
  HOMICIDE("Homicide", 0.0f),
  DRUG_CRIME("Drug Crime", 0.0f),
  VICE_CRIME("Vice Crime", 0.0f),
  CROWD_CONTROL("Crowd Control", 0.0f),
  MEDICAL_EMERGENCY("Medical Emergency", 0.0f),
  TRAUMA_RESPONSE("Trauma Response", 0.0f),
  CARDIAC_ARREST("Cardiac Arrest", 0.0f),
  POISONING_OVERDOSE("Poisoning/Overdose", 0.0f);

  private final String name;
  private final float probability;

  MissionType(String name, float probability) {
    this.name = name;
    this.probability = probability;
  }

  @Override
  public String toString() {
    return this.name;
  }

}