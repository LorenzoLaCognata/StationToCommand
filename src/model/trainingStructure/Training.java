package model.trainingStructure;

import model.experienceStructure.ExperienceLink;

import java.util.List;

public class Training {

	private final TrainingType trainingType;
	private final ExperienceLink experienceRequirement;
	private final List<TrainingLink> trainingRequirements;

    public Training(TrainingType trainingType, ExperienceLink experienceRequirement, List<TrainingLink> trainingRequirements) {
        this.trainingType = trainingType;
		this.experienceRequirement = experienceRequirement;
		this.trainingRequirements = trainingRequirements;
    }

	@Override
	public String toString() {
		return trainingType.toString();
	}

	public TrainingType getTrainingType() {
		return trainingType;
	}

}