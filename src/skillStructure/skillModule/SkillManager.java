package skillStructure.skillModule;

import experienceStructure.experienceModule.ExperienceManager;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.skillLinkModule.SkillLink;
import linkStructure.trainingLinkModule.TrainingLink;
import skillStructure.skillRequirementModule.SkillSkillRequirement;
import skillStructure.skillRequirementModule.SkillTrainingRequirement;
import trainingStructure.trainingModule.TrainingManager;
import trainingStructure.trainingModule.TrainingType;
import trainingStructure.trainingRequirementModule.TrainingExperienceRequirement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SkillManager {

	private final List<Skill> skills = new ArrayList<>();

	public SkillManager(ExperienceManager experienceManager, TrainingManager trainingManager) {
		initSkills(experienceManager, trainingManager);
	}

	@Override
	public String toString() {
		return skills.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public Skill getSkill(SkillType skillType) {
		return skills.stream()
				.filter(item -> item.getSkillType().equals(skillType))
				.findAny()
				.orElse(null);
	}

	public void addSkill(Skill skill) {
		this.skills.add(skill);
	}

	public void initSkills(ExperienceManager experienceManager, TrainingManager trainingManager) {

		ExperienceLink experienceRequirement = new TrainingExperienceRequirement(experienceManager.getExperience(1));
		List<SkillLink> skillRequirements = new ArrayList<>();
		List<TrainingLink> trainingRequirements = new ArrayList<>();
		addSkill(new Skill(SkillType.SELF_DEFENSE, experienceRequirement, skillRequirements, trainingRequirements));

		experienceRequirement = new TrainingExperienceRequirement(experienceManager.getExperience(2));
		skillRequirements = List.of(new SkillSkillRequirement(getSkill(SkillType.SELF_DEFENSE)));
		trainingRequirements = new ArrayList<>();
		addSkill(new Skill(SkillType.WEAPON_HANDLING, experienceRequirement, skillRequirements, trainingRequirements));

		experienceRequirement = new TrainingExperienceRequirement(experienceManager.getExperience(2));
		skillRequirements = new ArrayList<>();
		trainingRequirements = List.of(new SkillTrainingRequirement(trainingManager.getTraining(TrainingType.FIRST_AID)));
		addSkill(new Skill(SkillType.IV_INSERTION, experienceRequirement, skillRequirements, trainingRequirements));

		experienceRequirement = new TrainingExperienceRequirement(experienceManager.getExperience(3));
		skillRequirements = List.of(new SkillSkillRequirement(getSkill(SkillType.IV_INSERTION)));
		trainingRequirements = List.of(new SkillTrainingRequirement(trainingManager.getTraining(TrainingType.FIRST_AID)));
		addSkill(new Skill(SkillType.INTUBATION, experienceRequirement, skillRequirements, trainingRequirements));

	}

}