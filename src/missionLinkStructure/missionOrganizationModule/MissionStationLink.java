package missionLinkStructure.missionOrganizationModule;

import stationStructure.stationModule.Station;
import unitStructure.unitModule.Unit;
import linkStructure.organizationLinkModule.StationLink;
import linkStructure.organizationLinkModule.UnitLink;

import java.util.ArrayList;
import java.util.List;

public class MissionStationLink extends StationLink {

  private List<UnitLink> unitLinks;

  public MissionStationLink(Station station) {
    super(station);
    unitLinks = new ArrayList<>();
  }

  public void linkUnit(Unit unit) {
    // TODO
  }

}