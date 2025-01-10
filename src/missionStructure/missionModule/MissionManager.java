package missionStructure.missionModule;

import departmentStructure.departmentModule.DepartmentManager;
import locationStructure.locationModule.Location;
import locationStructure.locationModule.LocationManager;
import utilsStructure.utilsModule.Utils;

import java.util.ArrayList;
import java.util.List;

public class MissionManager {

    private final List<Mission> missions = new ArrayList<>();

    public MissionManager() {
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public Mission getMission(MissionType missionType, Location location) {
        return missions.stream()
                .filter(item -> item.getMissionType().equals(missionType) && item.getLocation().equals(location))
                .findFirst()
                .orElse(null);
    }

    public void addMission(Mission mission) {
        this.missions.add(mission);
    }

    public Mission generateMission(DepartmentManager departmentManager, LocationManager locationManager) {
        MissionType randomMissionType = Utils.getRandomEnumValue(MissionType.class);
        Mission mission = new Mission(randomMissionType, locationManager.generateLocation());
        addMission(mission);
        return mission;
    }

}