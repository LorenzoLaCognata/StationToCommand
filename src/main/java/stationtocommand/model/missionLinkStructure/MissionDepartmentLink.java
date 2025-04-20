package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentLink;
import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.stationStructure.Station;

import java.util.ArrayList;
import java.util.List;

public class MissionDepartmentLink extends DepartmentLink implements Comparable<MissionDepartmentLink> {

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

  @Override
  public int compareTo(MissionDepartmentLink other) {
    int result = this.mission.compareTo(other.getMission());
    if (result != 0) {
      return result;
    }
    result = this.getDepartment().compareTo(other.getDepartment());
    if (result != 0) {
      return result;
    }
    return Integer.compare(System.identityHashCode(this), System.identityHashCode(other));
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

}