package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationLink;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitLink;
import stationtocommand.model.unitStructure.UnitStatus;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.vehicleStructure.Vehicle;
import stationtocommand.model.vehicleStructure.VehicleLink;
import stationtocommand.model.vehicleStructure.VehicleStatus;
import stationtocommand.model.vehicleStructure.VehicleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MissionStationLink extends StationLink {

  private final Mission mission;
  private final List<MissionUnitLink> unitLinks;

  public MissionStationLink(Mission mission, Station station) {
    super(station);
    this.mission = mission;
    unitLinks = new ArrayList<>();
  }

  @Override
  public String toString() {
    return this.getStation().toString();
  }

  public Mission getMission() {
    return mission;
  }

  public List<MissionUnitLink> getUnitLinks() {
    return unitLinks;
  }

  public MissionUnitLink getUnitLink(Unit unit) {
    return unitLinks.stream()
            .filter(item -> item.getUnit().equals(unit))
            .findAny()
            .orElse(null);
  }

  public void linkUnit(Unit unit) {
    if (unitLinks.stream()
          .noneMatch(item -> item.getUnit().equals(unit))) {
      MissionUnitLink missionUnitLink = new MissionUnitLink(mission, unit);
      unitLinks.add(missionUnitLink);
    }
  }

  public Map<UnitStatus, Long> missionUnitsByStatus() {
    return getUnitLinks().stream()
            .map(UnitLink::getUnit)
            .collect(Collectors.groupingBy(
                    Unit::getUnitStatus,
                    Collectors.counting())
            );
  }

  public Map<UnitType, Map<UnitStatus, Long>> missionUnitsByTypeAndStatus() {
    return getUnitLinks().stream()
            .map(UnitLink::getUnit)
            .collect(Collectors.groupingBy(
                            Unit::getUnitType,
                            Collectors.groupingBy(
                                    Unit::getUnitStatus,
                                    Collectors.counting())
                    )
            );
  }

  public Map<VehicleStatus, Long> missionVehiclesByStatus() {
      return getUnitLinks().stream()
              .flatMap(missionUnitLink -> missionUnitLink.getVehicleLinks().stream())
              .map(VehicleLink::getVehicle)
              .collect(Collectors.groupingBy(
                    Vehicle::getVehicleStatus,
                    Collectors.counting())
              );
  }

  public Map<VehicleType, Map<VehicleStatus, Long>> missionVehiclesByTypeAndStatus() {
      return getUnitLinks().stream()
              .flatMap(missionUnitLink -> missionUnitLink.getVehicleLinks().stream())
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
      return getUnitLinks().stream()
              .flatMap(missionUnitLink -> missionUnitLink.getResponderLinks().stream())
              .map(ResponderLink::getResponder)
              .collect(Collectors.groupingBy(
                    Responder::getResponderStatus,
                    Collectors.counting())
              );
  }

  public Map<RankType, Map<ResponderStatus, Long>> missionRespondersByRankAndStatus() {
      return getUnitLinks().stream()
              .flatMap(missionUnitLink -> missionUnitLink.getResponderLinks().stream())
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