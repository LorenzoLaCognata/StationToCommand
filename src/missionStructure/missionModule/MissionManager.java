package missionStructure.missionModule;

import locationStructure.locationModule.Location;

import java.util.ArrayList;
import java.util.List;

public class MissionManager {

    private final List<Mission> missions = new ArrayList<>();

    public MissionManager() {
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

}