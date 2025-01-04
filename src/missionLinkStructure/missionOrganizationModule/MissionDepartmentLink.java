package missionLinkStructure.missionOrganizationModule;

import departmentStructure.departmentModule.Department;
import stationStructure.stationModule.Station;
import linkStructure.organizationLinkModule.DepartmentLink;
import linkStructure.organizationLinkModule.StationLink;

import java.util.ArrayList;
import java.util.List;

public class MissionDepartmentLink extends DepartmentLink {

  private List<StationLink> stationLinks;
  
  public MissionDepartmentLink(Department Department) {
    super(Department);
    stationLinks = new ArrayList<>();
  }

  public void linkStation(Station station) {
      // TODO
  }

}