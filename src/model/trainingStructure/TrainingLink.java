package model.trainingStructure;

public abstract class TrainingLink {

    private final Training training;

    public TrainingLink(Training training) {
        this.training = training;
    }

    @Override
    public String toString() {
        return "[TRAINING] " + training.getTrainingType().toString();
    }

    public Training getTraining() {
        return training;
    }
    
}