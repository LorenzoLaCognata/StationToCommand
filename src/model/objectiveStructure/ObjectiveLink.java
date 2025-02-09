package model.objectiveStructure;

public abstract class ObjectiveLink {

    private final Objective objective;

    public ObjectiveLink(Objective objective) {
        this.objective = objective;
    }

    @Override
    public String toString() {
        return objective.toString();
    }

    public Objective getObjective() {
        return objective;
    }

}