package departmentStructure.departmentModule;

import locationStructure.locationModule.LocationManager;
import rankStructure.rankModule.RankManager;
import stationStructure.stationModule.Station;
import stationStructure.stationModule.StationManager;

import java.util.List;

public class Department {

    private final DepartmentType departmentType;
    private final StationManager stationManager;
    private final RankManager rankManager;

    public Department(DepartmentType departmentType, LocationManager locationManager) {
        System.out.println("Department initializing");
        this.departmentType = departmentType;
        this.stationManager = new StationManager(this, locationManager);
        this.rankManager = new RankManager(this);
        System.out.println("Department initialized successfully");
    }

    @Override
    public String toString() {
        return "[DEPARTMENT] " + departmentType + " Department";
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    public StationManager getStationManager() {
        return stationManager;
    }

    public List<Station> getStations() {
        return stationManager.getStations();
    }

    public RankManager getRankManager() {
        return rankManager;
    }
}