package missionStructure.missionModule;

import locationStructure.locationModule.Location;

import java.util.ArrayList;
import java.util.List;

public class MissionManager {

    private List<Mission> missions = new ArrayList<>();

    public MissionManager() {
        System.out.println("MissionManager initializing");
        System.out.println("MissionManager initialized successfully");
    }

    public Mission getMission(Location location) {
        // TODO
        return missions.get(0);
    }

    public void addMission(Mission mission) {
        this.missions.add(mission);
    }

}