package stationtocommand.controller.simulationStructure;

public enum ScheduledEventType {
  MISSION_QUEUEING("Mission generation queued"),
  MISSION_GENERATION("Mission generation completed"),
  MISSION_DISPATCH_DEPARTMENT("Mission dispatch to department"),
  MISSION_DISPATCH_STATION("Mission dispatch to station"),
  MISSION_DISPATCH_UNIT("Mission dispatch to unit");

  private final String name;

  ScheduledEventType(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return this.name;
  }

}