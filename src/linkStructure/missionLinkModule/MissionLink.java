package linkStructure.missionLinkModule;

import missionStructure.missionModule.Mission;

public abstract class MissionLink {

    private final Mission mission;

    public MissionLink(Mission mission) {
        this.mission = mission;
    }

}