package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationLink;
import stationtocommand.model.unitStructure.Unit;

import java.util.ArrayList;
import java.util.List;

public class MissionStationLink extends StationLink implements Comparable<MissionStationLink>  {

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

  @Override
  public int compareTo(MissionStationLink other) {
    int result = this.mission.compareTo(other.getMission());
    if (result != 0) {
      return result;
    }
    result = this.getStation().compareTo(other.getStation());
    if (result != 0) {
      return result;
    }
    return Integer.compare(System.identityHashCode(this), System.identityHashCode(other));
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

}