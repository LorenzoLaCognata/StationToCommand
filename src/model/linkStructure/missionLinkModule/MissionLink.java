package model.linkStructure.missionLinkModule;

import model.missionStructure.missionModule.Mission;

public abstract class MissionLink {

    private final Mission mission;

    public MissionLink(Mission mission) {
        this.mission = mission;
    }

}