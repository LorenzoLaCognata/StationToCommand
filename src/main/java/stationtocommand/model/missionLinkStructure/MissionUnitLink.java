package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitLink;
import stationtocommand.model.vehicleStructure.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class MissionUnitLink extends UnitLink implements Comparable<MissionUnitLink>  {

  private final Mission mission;
  private final List<MissionResponderLink> responderLinks;
  private final List<MissionVehicleLink> vehicleLinks;

  public MissionUnitLink(Mission mission, Unit unit) {
    super(unit);
    this.mission = mission;
    responderLinks = new ArrayList<>();
    vehicleLinks = new ArrayList<>();
  }

  @Override
  public String toString() {
    return this.getUnit().toString();
  }

  @Override
  public int compareTo(MissionUnitLink other) {
    int result = this.mission.compareTo(other.getMission());
    if (result != 0) {
      return result;
    }
    return this.getUnit().compareTo(other.getUnit());
  }

  public Mission getMission() {
    return mission;
  }

  public List<MissionResponderLink> getResponderLinks() {
    return responderLinks;
  }

  public List<MissionVehicleLink> getVehicleLinks() {
    return vehicleLinks;
  }

  public void linkResponder(Responder responder) {
    if (responderLinks.stream()
            .noneMatch(item -> item.getResponder().equals(responder))) {
      MissionResponderLink missionResponderLink = new MissionResponderLink(mission, responder);
      responderLinks.add(missionResponderLink);
    }
  }

  public void linkVehicle(Vehicle vehicle) {
    if (vehicleLinks.stream()
            .noneMatch(item -> item.getVehicle().equals(vehicle))) {
      MissionVehicleLink missionVehicleLink = new MissionVehicleLink(mission, vehicle);
      vehicleLinks.add(missionVehicleLink);
    }
  }

}