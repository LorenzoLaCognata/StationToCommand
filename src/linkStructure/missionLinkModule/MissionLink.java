package linkStructure.missionLinkModule;

import missionStructure.missionModule.Mission;

public abstract class MissionLink {

    private Mission mission;

    public MissionLink(Mission mission) {
        System.out.println("MissionLink initializing");
        this.mission = mission;
        System.out.println("MissionLink initialized successfully");
    }

}