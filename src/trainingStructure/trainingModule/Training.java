package trainingStructure.trainingModule;

import experienceStructure.experienceModule.Experience;
import responderStructure.responderModule.Responder;
import unitStructure.unitTypeModule.UnitType;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.responderLinkModule.ResponderLink;
import linkStructure.trainingLinkModule.TrainingLink;

import java.util.List;

public class Training {

	private UnitType unitType;
	private String name;
	private List<ResponderLink> responderLinks;
	private List<ExperienceLink> experienceRequirements;
	private List<TrainingLink> trainingRequirements;

    public Training(UnitType unitType, String name, List<ExperienceLink> experienceRequirements, List<TrainingLink> trainingRequirements) {
        System.out.println("Training initializing");
        this.unitType = unitType;
		this.name = name;
		this.experienceRequirements = experienceRequirements;
		this.trainingRequirements = trainingRequirements;
        System.out.println("Training initialized successfully");
    }

	public void linkResponder(Responder responder) {
		// TODO
	}

	public void requireExperience(Experience experience) {
		// TODO
	}

	public void requireTraining(Training training) {
		// TODO
	}


}