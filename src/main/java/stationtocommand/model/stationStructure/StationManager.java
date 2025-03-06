package stationtocommand.model.stationStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.locationStructure.LocationManager;
import stationtocommand.model.unitStructure.UnitStatus;
import stationtocommand.model.unitTypeStructure.UnitType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StationManager {

	private final List<Station> stations = new ArrayList<>();
	private int stationNumberCounter = 0;
	private int unitNumberCounter = 0;

    public StationManager(Department department) {
		initStations(department);
    }

	@Override
	public String toString() {
		return stations.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public List<Station> getStations() {
		return stations;
	}

	public Station getStation(int number) {
		return stations.stream()
				.filter(item -> item.getNumber() == number)
				.findAny()
				.orElse(null);
	}

	public void addStation(Station station) {
		this.stations.add(station);
	}

	public List<Station> stationsMatchingAllUnitTypes(List<UnitType> unitTypes) {
		return stations.stream()
				.filter(station -> unitTypes.stream()
					.allMatch(type -> station.getUnits()
						.stream().anyMatch(unit -> unit.getUnitType().equals(type))))
				.toList();
	}

	public List<Station> stationsMatchingUnitType(UnitType unitType) {
		return stations.stream()
				.filter(station -> station.getUnits()
					.stream().anyMatch(unit -> unit.getUnitType().equals(unitType)))
				.toList();
	}

	public List<Station> availableStationsMatchingAllUnitTypes(List<UnitType> unitTypes) {
		return stationsMatchingAllUnitTypes(unitTypes).stream()
			.filter(station -> station.getUnits().stream()
				.anyMatch(unit -> unit.getUnitStatus().equals(UnitStatus.AVAILABLE)))
			.toList();
	}

	public List<Station> availableStationsMatchingUnitType(UnitType unitType) {
		return stationsMatchingUnitType(unitType).stream()
			.filter(station -> station.getUnits().stream()
				.anyMatch(unit -> unit.getUnitStatus().equals(UnitStatus.AVAILABLE)))
			.toList();
	}

	public int nextStationNumber() {
		this.stationNumberCounter++;
		return stationNumberCounter;
	}

	public int nextUnitNumber() {
		this.unitNumberCounter++;
		return unitNumberCounter;
	}

	public void initStations(Department department) {

		switch (department.getDepartmentType()) {
			case FIRE_DEPARTMENT -> {
				addStation(new Station(department, nextStationNumber(), new Location(40.113821f, -88.245504f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.127386f, -88.253492f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.126209f, -88.219916f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.104133f, -88.222438f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.110017f, -88.204630f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.090293f, -88.191698f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.092407f, -88.260449f), this));
                addStation(new Station(department, nextStationNumber(), new Location(40.107123f, -88.275456f), this));
			}
			case POLICE_DEPARTMENT ->  {
                addStation(new Station(department, nextStationNumber(), new Location(40.1179555f, -88.2408512f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.1212780f, -88.2708330f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.0899440f, -88.2711164f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.1019990f, -88.2102220f), this));
			}
			case MEDIC_DEPARTMENT -> {
				addStation(new Station(department, nextStationNumber(), new Location(40.125500f, -88.240000f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.110000f, -88.200000f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.098000f, -88.270000f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.095000f, -88.220000f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.112500f, -88.230000f), this));
				addStation(new Station(department, nextStationNumber(), new Location(40.115000f, -88.262000f), this));
			}
		}

	}

}