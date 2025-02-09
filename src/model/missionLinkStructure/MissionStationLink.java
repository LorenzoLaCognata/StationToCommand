package model.missionLinkStructure;

import model.stationStructure.Station;
import model.stationStructure.StationLink;
import model.unitStructure.Unit;

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