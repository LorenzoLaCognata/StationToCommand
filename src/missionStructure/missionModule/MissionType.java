package missionStructure.missionModule;

public enum MissionType {
  STRUCTURE_FIRE("Structure Fire"),
  VEHICLE_FIRE("Vehicle Fire"),
  WATER_RESCUE("Water Rescue"),
  COLLAPSE_RESCUE("Collapse Rescue"),
  VEHICLE_EXTRICATION("Vehicle Extrication"),
  ANIMAL_RESCUE("Animal Rescue");

  /*
    TRAFFIC_INCIDENT: Managing road accidents or directing traffic after disruptions.
    BURGLARY_IN_PROGRESS: Responding to active break-ins.
    DOMESTIC_DISTURBANCE: Addressing disputes or violence in homes.
    SUSPECT_APPREHENSION: Pursuing or arresting individuals involved in criminal activity.
    CROWD_CONTROL: Maintaining order during events or protests.
    WELFARE_CHECK: Ensuring the safety of vulnerable individuals.

    MEDICAL_EMERGENCY: Treating patients with acute medical conditions (e.g., heart attacks, strokes).
    TRAUMA_RESPONSE: Addressing injuries from accidents or violence (e.g., gunshot wounds).
    CARDIAC_ARREST: Providing CPR and advanced life support for cardiac emergencies.
    POISONING_OR_OVERDOSE: Assisting individuals exposed to toxic substances or overdosed.
    MATERNITY_EMERGENCY: Responding to childbirth complications or labor emergencies.
    PANDEMIC_RESPONSE: Handling mass medical crises or infectious disease outbreaks.

    VEHICLE_COLLISION: Police manage traffic, Fire extricate victims, EMS treat injuries.
    NATURAL_DISASTER_RESPONSE: Coordinated response to earthquakes, floods, or hurricanes.
    ACTIVE_SHOOTER: Police neutralize the threat, EMS treat victims, and Fire assist with evacuation.
    EXPLOSION_RESPONSE: Fire fights the fire, Police secure the area, EMS treat the injured.
    MISSING_PERSON_SEARCH
 */

  private final String name;

  MissionType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}