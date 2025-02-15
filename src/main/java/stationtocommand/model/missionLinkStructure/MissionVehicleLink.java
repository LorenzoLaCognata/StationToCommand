package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleLink;

public class MissionVehicleLink extends VehicleLink {

  public MissionVehicleLink(Vehicle vehicle) {
    super(vehicle);
  }

  @Override
  public String toString() {
    return this.getVehicle().toString();
  }

}