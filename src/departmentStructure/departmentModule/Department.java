package departmentStructure.departmentModule;

import locationStructure.locationModule.LocationManager;
import rankStructure.rankModule.RankManager;
import shiftStructure.shiftModule.ShiftManager;
import stationStructure.stationModule.Station;
import stationStructure.stationModule.StationManager;

import java.util.List;

public class Department {

    private final DepartmentType departmentType;
    private final StationManager stationManager;
    private final RankManager rankManager;
    private final ShiftManager shiftManager;

    public Department(DepartmentType departmentType, LocationManager locationManager) {
        this.departmentType = departmentType;
        this.stationManager = new StationManager(this, locationManager);
        this.rankManager = new RankManager(this);
        this.shiftManager = new ShiftManager(this);
    }

    @Override
    public String toString() {
        return departmentType + " Department";
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

    public ShiftManager getShiftManager() {
        return shiftManager;
    }

}