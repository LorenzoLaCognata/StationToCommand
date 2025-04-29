package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitLink;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleLink;
import stationtocommand.model.vehicleStructure.VehicleStatus;
import stationtocommand.model.vehicleStructure.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MissionUnitLink extends UnitLink {

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

  public Map<VehicleStatus, Long> missionVehiclesByStatus() {
    return getVehicleLinks().stream()
            .map(VehicleLink::getVehicle)
            .collect(Collectors.groupingBy(
                    Vehicle::getVehicleStatus,
                    Collectors.counting())
            );
  }

  public Map<VehicleType, Map<VehicleStatus, Long>> missionVehiclesByTypeAndStatus() {
    return getVehicleLinks().stream()
            .map(VehicleLink::getVehicle)
            .collect(Collectors.groupingBy(
                            Vehicle::getVehicleType,
                            Collectors.groupingBy(
                                    Vehicle::getVehicleStatus,
                                    Collectors.counting())
                    )
            );
  }

  public Map<ResponderStatus, Long> missionRespondersByStatus() {
    return getResponderLinks().stream()
            .map(ResponderLink::getResponder)
            .collect(Collectors.groupingBy(
                    Responder::getResponderStatus,
                    Collectors.counting())
            );
  }

  public Map<RankType, Map<ResponderStatus, Long>> missionRespondersByRankAndStatus() {
    return getResponderLinks().stream()
            .map(ResponderLink::getResponder)
            .collect(Collectors.groupingBy(
                            responder -> responder.getRank().getRankType(),
                            Collectors.groupingBy(
                                    Responder::getResponderStatus,
                                    Collectors.counting())
                    )
            );
  }

}