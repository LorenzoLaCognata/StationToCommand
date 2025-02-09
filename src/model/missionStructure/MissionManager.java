package model.missionStructure;

import model.departmentStructure.DepartmentManager;
import model.locationStructure.Location;
import model.locationStructure.LocationManager;
import model.utilsStructure.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MissionManager {

    private final List<Mission> missions = new ArrayList<>();

    public MissionManager() {
    }

    @Override
    public String toString() {
        return missions.stream()
                .map(item -> "\t- " + item)
                .collect(Collectors.joining("\n"));
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public List<Mission> getMissions(MissionType missionType, Location location) {
        return missions.stream()
                .filter(item -> item.getMissionType().equals(missionType) && item.getLocation().equals(location))
                .toList();
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