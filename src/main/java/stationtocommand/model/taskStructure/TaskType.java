package stationtocommand.model.taskStructure;

public enum TaskType {
  INSPECT_FIRE_SCENE("Inspect Fire Scene"),
  ESTABLISH_FIRE_PERIMETER("Establish Fire Perimeter"),
  SEARCH_AND_RESCUE("Search and Rescue"),
  VENTILATE_STRUCTURE("Ventilate Structure"),
  EXTINGUISH_FIRE("Extinguish Fire"),
  CHECK_HOT_SPOTS("Check Hot Spots"),
  SALVAGE_PROPERTY("Salvage Property"),
  REMOVE_DEBRIS("Remove Debris"),
  ASSESS_FIRE_DAMAGE("Assess Fire Damage"),
  PROVIDE_SUPPORT_TO_VICTIM("Provide Support to Victim"),
  MONITOR_FIRE_SPREAD("Monitor Fire Spread"),
  CONTROL_HAZARDOUS_MATERIALS("Control Hazardous Materials"),

  INVESTIGATE_CRIME_SCENE("Investigate Crime Scene"),
  SECURE_PERIMETER("Secure Perimeter"),
  INTERVIEW_WITNESS("Interview Witness"),
  GATHER_EVIDENCE("Gather Evidence"),
  APPREHEND_SUSPECT("Apprehend Suspect"),
  PERFORM_CROWD_CONTROL("Perform Crowd Control"),
  SEARCH_VEHICLE("Search Vehicle"),
  RESPOND_TO_ALARM("Respond to Alarm"),
  CONDUCT_PURSUIT("Conduct Pursuit"),
  MANAGE_DOMESTIC_DISPUTE("Manage Domestic Dispute"),
  CLEAR_BUILDING("Clear Building"),
  PATROL_AREA("Patrol Area"),

  ASSESS_VICTIM("Assess Victim"),
  CHECK_AIRWAY("Check Airway"),
  ADMINISTER_OXYGEN("Administer Oxygen"),
  PERFORM_CPR("Perform CPR"),
  STABILIZE_PATIENT("Stabilize Patient"),
  CONTROL_BLEEDING("Control Bleeding"),
  DEFIBRILLATE_PATIENT("Defibrillate Patient"),
  MEASURE_VITAL_SIGNS("Measure Vital Signs"),
  TRANSPORT_PATIENT("Transport Patient"),
  ASSIST_WITH_CHILD_BIRTH("Assist with Child Birth"),
  TREAT_OVERDOSE("Treat Overdose"),
  USE_EPI_PEN("Use Epi-Pen");

  private final String name;

  TaskType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}