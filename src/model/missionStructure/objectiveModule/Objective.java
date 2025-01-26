package model.missionStructure.objectiveModule;

public class Objective {

    private final ObjectiveType objectiveType;

    public Objective(ObjectiveType objectiveType) {
        this.objectiveType = objectiveType;
    }

    @Override
    public String toString() {
        return "[OBJECTIVE] " + objectiveType;
    }

}