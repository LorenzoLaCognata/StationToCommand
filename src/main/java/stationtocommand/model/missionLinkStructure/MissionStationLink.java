package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationLink;
import stationtocommand.model.unitStructure.Unit;

import java.util.ArrayList;
import java.util.List;

public class MissionStationLink extends StationLink {

  private final List<MissionUnitLink> unitLinks;

  public MissionStationLink(Station station) {
    super(station);
    unitLinks = new ArrayList<>();
  }

  @Override
  public String toString() {
    return this.getStation().toString();
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