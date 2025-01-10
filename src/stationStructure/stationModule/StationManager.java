package stationStructure.stationModule;

import departmentStructure.departmentModule.Department;
import locationStructure.locationModule.LocationManager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class StationManager {

    private final List<Station> stations = new ArrayList<>();
	private int nextStationNumber = 1;
	private int nextUnitNumber = 1;

    public StationManager(Department department, LocationManager locationManager) {
		initStations(department, locationManager);
    }

	@Override
	public String toString() {
		return stations.stream()
				.map(station -> "\t- " + station)
				.collect(Collectors.joining("\n"));
	}

	public List<Station> getStations() {
		return stations;
	}

	public Station getStation(int number) {
		return stations.stream()
				.filter(item -> item.getNumber() == number)
				.findFirst()
				.orElse(null);
	}

	public int getNextUnitNumber() {
		return nextUnitNumber;
	}

	public void setNextUnitNumber(int nextUnitNumber) {
		this.nextUnitNumber = nextUnitNumber;
	}

	public void addStation(Station station) {
		this.stations.add(station);
		this.nextStationNumber++;
	}
	
	public void initStations(Department department, LocationManager locationManager) {
        addStation(new Station(department, nextStationNumber, locationManager.generateLocation(), this));
		addStation(new Station(department, nextStationNumber, locationManager.generateLocation(), this));
		addStation(new Station(department, nextStationNumber, locationManager.generateLocation(), this));
	}


}