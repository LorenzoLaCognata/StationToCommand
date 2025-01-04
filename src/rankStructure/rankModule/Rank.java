package rankStructure.rankModule;

import departmentStructure.departmentModule.DepartmentType;
import experienceStructure.experienceModule.Experience;
import skillStructure.skillModule.Skill;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.rankLinkModule.RankLink;
import linkStructure.skillLinkModule.SkillLink;

import java.util.List;

public class Rank {

	private DepartmentType departmentType;
	private String name;
	private List<RankLink> rankRequirements;
	private List<ExperienceLink> experienceRequirements;
	private List<SkillLink> skillRequirements;

	public Rank(DepartmentType departmentType, String name, List<RankLink> rankRequirements, List<ExperienceLink>
		experienceRequirements, List<SkillLink> skillRequirements) {
	 	System.out.println("Rank initializing");
		this.departmentType = departmentType;
		this.name = name;
		this.rankRequirements = rankRequirements;
		this.experienceRequirements = experienceRequirements;
		this.skillRequirements = skillRequirements;
		System.out.println("Rank initialized successfully");
	}

	public void requireRank(Rank rank) {
		// TODO
	}

	public void requireExperience(Experience experience) {
		// TODO
	}

	public void requireSkill(Skill skill) {
		// TODO
	}

}