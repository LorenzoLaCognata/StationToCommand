package linkStructure.trainingLinkModule;

import trainingStructure.trainingModule.Training;

public abstract class TrainingLink {

    private Training training;

    public TrainingLink(Training training) {
        System.out.println("TrainingLink initializing");
        this.training = training;
		System.out.println("TrainingLink initialized successfully");
    }
	
}