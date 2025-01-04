package stationStructure.stationModule;

import departmentStructure.departmentModule.DepartmentType;

import java.util.ArrayList;
import java.util.List;

public class StationManager {

    private List<Station> stations = new ArrayList<>();

    public StationManager(DepartmentType departmentType) {
      System.out.println("StationManager initializing");
			initStations(departmentType);
      System.out.println("StationManager initialized successfully");
    }

	public Station getStation(int number) {
		// TODO
		return stations.get(0);
	}

	public void addStation(Station station) {
		this.stations.add(station);
	}
	
	public void initStations(DepartmentType departmentType) {
        addStation(new Station(departmentType));
	}


}