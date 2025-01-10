package skillStructure.skillModule;

import experienceStructure.experienceModule.Experience;
import responderStructure.responderModule.Responder;
import trainingStructure.trainingModule.Training;
import unitStructure.unitTypeModule.UnitType;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.responderLinkModule.ResponderLink;
import linkStructure.skillLinkModule.SkillLink;
import linkStructure.trainingLinkModule.TrainingLink;

import java.util.ArrayList;
import java.util.List;

public class Skill {

	private final UnitType unitType;
	private final String name;
	private final List<ExperienceLink> experienceRequirements;
	private final List<SkillLink> skillRequirements;
	private final List<TrainingLink> trainingRequirements;
	private final List<ResponderLink> responderLinks;

	public Skill(UnitType unitType, String name, List<ExperienceLink> experienceRequirements,
			List<SkillLink> skillRequirements, List<TrainingLink> trainingRequirements) {
		System.out.println("Skill initializing");
		this.unitType = unitType;
		this.name = name;
		this.experienceRequirements = experienceRequirements;
		this.skillRequirements = skillRequirements;
		this.trainingRequirements = trainingRequirements;
		this.responderLinks = new ArrayList<>();
		System.out.println("Skill initialized successfully");
	}

	public void linkResponder(Responder responder) {
		// TODO
	}

	public void requireExperience(Experience experience) {
		// TODO
	}

	public void requireSkill(Skill skill) {
		// TODO
	}

	public void requireTraining(Training training) {
		// TODO
	}

}