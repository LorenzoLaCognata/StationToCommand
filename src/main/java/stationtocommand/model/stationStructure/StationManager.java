package stationtocommand.model.stationStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.locationStructure.LocationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StationManager {

    private final List<Station> stations = new ArrayList<>();
	private int stationNumberCounter = 0;
	private int unitNumberCounter = 0;

    public StationManager(Department department, LocationManager locationManager) {
		initStations(department, locationManager);
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

	public int nextStationNumber() {
		this.stationNumberCounter++;
		return stationNumberCounter;
	}

	public int nextUnitNumber() {
		this.unitNumberCounter++;
		return unitNumberCounter;
	}

	public void initStations(Department department, LocationManager locationManager) {
        addStation(new Station(department, nextStationNumber(), locationManager.generateLocation(), this));
		addStation(new Station(department, nextStationNumber(), locationManager.generateLocation(), this));
		addStation(new Station(department, nextStationNumber(), locationManager.generateLocation(), this));
		if (!department.getDepartmentType().equals(DepartmentType.POLICE_DEPARTMENT)) {
			addStation(new Station(department, nextStationNumber(), locationManager.generateLocation(), this));
			if (!department.getDepartmentType().equals(DepartmentType.MEDIC_DEPARTMENT)) {
				addStation(new Station(department, nextStationNumber(), locationManager.generateLocation(), this));
			}
		}
	}

}