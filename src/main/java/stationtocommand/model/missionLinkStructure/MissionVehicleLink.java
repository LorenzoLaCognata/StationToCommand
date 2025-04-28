package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleLink;

public class MissionVehicleLink extends VehicleLink implements Comparable<MissionVehicleLink>  {

  private final Mission mission;

  public MissionVehicleLink(Mission mission, Vehicle vehicle) {
    super(vehicle);
    this.mission = mission;
  }

  @Override
  public String toString() {
    return this.getVehicle().toString();
  }

  @Override
  public int compareTo(MissionVehicleLink other) {
    int result = this.mission.compareTo(other.getMission());
    if (result != 0) {
      return result;
    }
    return this.getVehicle().compareTo(other.getVehicle());
  }

  public Mission getMission() {
    return mission;
  }

}