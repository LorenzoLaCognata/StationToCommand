package stationtocommand.model.missionLinkStructure;

import stationtocommand.model.missionStructure.Mission;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.stationStructure.StationLink;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitStructure.UnitStatus;

import java.util.ArrayList;
import java.util.List;

public class MissionStationLink extends StationLink {

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

  public Mission getMission() {
    return mission;
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