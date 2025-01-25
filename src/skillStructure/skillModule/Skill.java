package skillStructure.skillModule;

import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.personLinkModule.ResponderLink;
import linkStructure.skillLinkModule.SkillLink;
import linkStructure.trainingLinkModule.TrainingLink;
import responderStructure.responderModule.Responder;

import java.util.ArrayList;
import java.util.List;

public class Skill {

	private final SkillType skillType;
	private final ExperienceLink experienceRequirement;
	private final List<SkillLink> skillRequirements;
	private final List<TrainingLink> trainingRequirements;

	public Skill(SkillType skillType, ExperienceLink experienceRequirement,
			List<SkillLink> skillRequirements, List<TrainingLink> trainingRequirements) {
		this.skillType = skillType;
		this.experienceRequirement = experienceRequirement;
		this.skillRequirements = skillRequirements;
		this.trainingRequirements = trainingRequirements;
	}

	@Override
	public String toString() {
		return skillType.toString();
	}

	public SkillType getSkillType() {
		return skillType;
	}

}