package stationtocommand.model.unitStructure;

import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitTypeStructure.UnitType;

public class MedicUnit extends Unit {

  public MedicUnit(Station station, UnitType unitType, int number) {
      super(station, unitType, number);
  }

}