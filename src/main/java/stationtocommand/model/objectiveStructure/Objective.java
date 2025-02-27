package stationtocommand.model.objectiveStructure;

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