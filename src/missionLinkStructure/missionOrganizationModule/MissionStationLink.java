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

  public MissionUnitLink linkUnit(Unit unit) {
    if (unitLinks.stream()
            .noneMatch(item -> item.getUnit().getUnitType().equals(unit.getUnitType()) &&
                    item.getUnit().getNumber() == unit.getNumber())) {
      MissionUnitLink missionUnitLink = new MissionUnitLink(unit);
      unitLinks.add(missionUnitLink);
      return missionUnitLink;
    }
    else {
      return null;
    }
  }

}