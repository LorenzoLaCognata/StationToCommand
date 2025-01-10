package trainingStructure.trainingModule;

import experienceStructure.experienceModule.Experience;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.responderLinkModule.ResponderLink;
import linkStructure.trainingLinkModule.TrainingLink;
import responderStructure.responderModule.Responder;
import unitStructure.unitTypeModule.UnitType;

import java.util.ArrayList;
import java.util.List;

public class Training {

	private final UnitType unitType;
	private final String name;
	private final List<ExperienceLink> experienceRequirements;
	private final List<TrainingLink> trainingRequirements;
	private final List<ResponderLink> responderLinks;

    public Training(UnitType unitType, String name, List<ExperienceLink> experienceRequirements, List<TrainingLink> trainingRequirements) {
        System.out.println("Training initializing");
        this.unitType = unitType;
		this.name = name;
		this.experienceRequirements = experienceRequirements;
		this.trainingRequirements = trainingRequirements;
		this.responderLinks = new ArrayList<>();
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