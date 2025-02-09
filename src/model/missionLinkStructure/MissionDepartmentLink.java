package model.missionLinkStructure;

import model.departmentStructure.Department;
import model.departmentStructure.DepartmentLink;
import model.stationStructure.Station;

import java.util.ArrayList;
import java.util.List;

public class MissionDepartmentLink extends DepartmentLink {

  private final List<MissionStationLink> stationLinks;
  
  public MissionDepartmentLink(Department department) {
    super(department);
    stationLinks = new ArrayList<>();
  }

  public List<MissionStationLink> getStationLinks() {
    return stationLinks;
  }

  public void linkStation(Station station) {
    if (stationLinks.stream()
            .noneMatch(item -> item.getStation().equals(station))) {
      MissionStationLink missionStationLink = new MissionStationLink(station);
      stationLinks.add(missionStationLink);
    }
  }

}