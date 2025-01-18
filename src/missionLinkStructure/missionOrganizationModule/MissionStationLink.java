package missionLinkStructure.missionOrganizationModule;

import linkStructure.organizationLinkModule.StationLink;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.Unit;

import java.util.ArrayList;
import java.util.List;

public class MissionStationLink extends StationLink {

  private final List<MissionUnitLink> unitLinks;

  public MissionStationLink(Station station) {
    super(station);
    unitLinks = new ArrayList<>();
  }

  public List<MissionUnitLink> getUnitLinks() {
    return unitLinks;
  }

  public void linkUnit(Unit unit) {
    if (unitLinks.stream()
            .noneMatch(item -> item.getUnit().equals(unit))) {
      MissionUnitLink missionUnitLink = new MissionUnitLink(unit);
      unitLinks.add(missionUnitLink);
    }
  }

}