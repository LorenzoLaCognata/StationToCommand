package skillStructure.skillModule;

import unitStructure.unitTypeModule.FireUnitType;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.skillLinkModule.SkillLink;
import linkStructure.trainingLinkModule.TrainingLink;

import java.util.ArrayList;
import java.util.List;

public class SkillManager {

	private List<Skill> skills = new ArrayList<>();

	public SkillManager() {
		System.out.println("SkillManager initializing");
		initSkills();
		System.out.println("SkillManager initialized successfully");
	}

	public Skill getSkill(String name) {
		// TODO
		return skills.get(0);
	}

	public void addSkill(Skill skill) {
		this.skills.add(skill);
	}

	public void initSkills() {

		// TODO: loop through UnitTypes
		List<ExperienceLink> experienceRequirements = new ArrayList<>();
		List<SkillLink> skillRequirements = new ArrayList<>();
		List<TrainingLink> trainingRequirements = new ArrayList<>();
		Skill skill = new Skill(FireUnitType.FIRE_ENGINE, "First Aid Skill", experienceRequirements, skillRequirements, trainingRequirements);
		addSkill(skill);

	}

}