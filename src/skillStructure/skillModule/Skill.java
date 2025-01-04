package skillStructure.skillModule;

import experienceStructure.experienceModule.Experience;
import responderStructure.responderModule.Responder;
import trainingStructure.trainingModule.Training;
import unitStructure.unitTypeModule.UnitType;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.responderLinkModule.ResponderLink;
import linkStructure.skillLinkModule.SkillLink;
import linkStructure.trainingLinkModule.TrainingLink;

import java.util.List;

public class Skill {

	private UnitType unitType;
	private String name;
	private List<ResponderLink> responderLinks;
	private List<ExperienceLink> experienceRequirements;
	private List<SkillLink> skillRequirements;
	private List<TrainingLink> trainingRequirements;

	public Skill(UnitType unitType, String name, List<ExperienceLink> experienceRequirements,
			List<SkillLink> skillRequirements, List<TrainingLink> trainingRequirements) {
		System.out.println("Skill initializing");
		this.unitType = unitType;
		this.name = name;
		this.experienceRequirements = experienceRequirements;
		this.skillRequirements = skillRequirements;
		this.trainingRequirements = trainingRequirements;
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