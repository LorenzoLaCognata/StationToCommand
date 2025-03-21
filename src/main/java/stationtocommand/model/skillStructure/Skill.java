package stationtocommand.model.skillStructure;

import stationtocommand.model.experienceStructure.ExperienceLink;
import stationtocommand.model.trainingStructure.TrainingLink;

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