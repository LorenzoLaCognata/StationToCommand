package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleLink;

public class MissionVehicleLink extends VehicleLink {

  private final Mission mission;

  public MissionVehicleLink(Mission mission, Vehicle vehicle) {
    super(vehicle);
    this.mission = mission;
  }

  @Override
  public String toString() {
    return this.getVehicle().toString();
  }

  public Mission getMission() {
    return mission;
  }

}