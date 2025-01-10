package missionStructure.missionObjectiveModule;

public class MissionObjectiveLink {

    private final MissionObjective missionObjective;

    public MissionObjectiveLink(MissionObjective missionObjective) {
        System.out.println("MissionObjectiveLink initializing");
        this.missionObjective = missionObjective;
        System.out.println("MissionObjectiveLink initialized successfully");
    }

}