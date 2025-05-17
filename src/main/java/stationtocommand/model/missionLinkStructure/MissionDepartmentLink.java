package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.responderStructure.Responder;
import stationtocommand.model.responderStructure.ResponderLink;
import stationtocommand.model.responderStructure.ResponderStatus;
import stationtocommand.model.stationStructure.Station;
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

public class MissionDepartmentLink extends DepartmentLink {

  private final Mission mission;
  private final List<MissionStationLink> stationLinks;
  
  public MissionDepartmentLink(Mission mission, Department department) {
    super(department);
    this.mission = mission;
    this.stationLinks = new ArrayList<>();
  }

  @Override
  public String toString() {
    return this.getDepartment().toString();
  }

  public Mission getMission() {
    return mission;
  }

  public List<MissionStationLink> getStationLinks() {
    return stationLinks;
  }

  public MissionStationLink getStationLink(Station station) {
      return stationLinks.stream()
            .filter(item -> item.getStation().equals(station))
            .findAny()
            .orElse(null);
  }

  public void linkStation(Station station) {
    if (stationLinks.stream()
            .noneMatch(item -> item.getStation().equals(station))) {
      MissionStationLink missionStationLink = new MissionStationLink(mission, station);
      stationLinks.add(missionStationLink);
    }
  }

    public void clearStationLinks() {
        stationLinks.clear();
    }

  public Map<UnitStatus, Long> missionUnitsByStatus() {
    return getStationLinks().stream()
            .flatMap(missionStationLink -> missionStationLink.getUnitLinks().stream())
            .map(UnitLink::getUnit)
            .collect(Collectors.groupingBy(
                    Unit::getUnitStatus,
                    Collectors.counting())
            );
  }

  public Map<UnitType, Map<UnitStatus, Long>> missionUnitsByTypeAndStatus() {
    return getStationLinks().stream()
            .flatMap(missionStationLink -> missionStationLink.getUnitLinks().stream())
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
    return getStationLinks().stream()
            .flatMap(missionStationLink -> missionStationLink.getUnitLinks().stream())
            .flatMap(missionUnitLink -> missionUnitLink.getVehicleLinks().stream())
            .map(VehicleLink::getVehicle)
            .collect(Collectors.groupingBy(
                    Vehicle::getVehicleStatus,
                    Collectors.counting())
            );
  }

  public Map<VehicleType, Map<VehicleStatus, Long>> missionVehiclesByTypeAndStatus() {
      return getStationLinks().stream()
              .flatMap(missionStationLink -> missionStationLink.getUnitLinks().stream())
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
      return getStationLinks().stream()
              .flatMap(missionStationLink -> missionStationLink.getUnitLinks().stream())
              .flatMap(missionUnitLink -> missionUnitLink.getResponderLinks().stream())
              .map(ResponderLink::getResponder)
              .collect(Collectors.groupingBy(
                    Responder::getResponderStatus,
                    Collectors.counting())
              );
  }

  public Map<RankType, Map<ResponderStatus, Long>> missionRespondersByRankAndStatus() {
      return getStationLinks().stream()
              .flatMap(missionStationLink -> missionStationLink.getUnitLinks().stream())
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