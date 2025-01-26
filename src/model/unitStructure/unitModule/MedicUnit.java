package model.unitStructure.unitModule;

import model.stationStructure.stationModule.Station;
import model.unitStructure.unitTypeModule.UnitType;

public class MedicUnit extends Unit {

  public MedicUnit(Station station, UnitType unitType, int number) {
      super(station, unitType, number);
  }

}