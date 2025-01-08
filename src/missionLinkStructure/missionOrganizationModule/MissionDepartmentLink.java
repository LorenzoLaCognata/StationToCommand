package missionLinkStructure.missionOrganizationModule;

import departmentStructure.departmentModule.Department;
import missionStructure.missionModule.Mission;
import stationStructure.stationModule.Station;
import linkStructure.organizationLinkModule.DepartmentLink;
import linkStructure.organizationLinkModule.StationLink;

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

  public MissionStationLink linkStation(Station station) {
    if (stationLinks.stream()
            .noneMatch(item -> item.getStation().getDepartmentType().equals(station.getDepartmentType()) &&
                               item.getStation().getNumber() == station.getNumber())) {
      MissionStationLink missionStationLink = new MissionStationLink(station);
      stationLinks.add(missionStationLink);
      return missionStationLink;
    }
    else {
      return null;
    }
  }

}