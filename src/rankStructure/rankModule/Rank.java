package rankStructure.rankModule;

import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.rankLinkModule.RankLink;
import linkStructure.skillLinkModule.SkillLink;

import java.util.List;

public class Rank {

	private final int level;
	private final String name;
	private final List<RankLink> rankRequirements;
	private final List<ExperienceLink> experienceRequirements;
	private final List<SkillLink> skillRequirements;

	public Rank(int level, String name, List<RankLink> rankRequirements, List<ExperienceLink>
		experienceRequirements, List<SkillLink> skillRequirements) {
		this.level = level;
		this.name = name;
		this.rankRequirements = rankRequirements;
		this.experienceRequirements = experienceRequirements;
		this.skillRequirements = skillRequirements;
	}

	@Override
	// TODO: associate appropriate symbols/images to ranks
	public String toString() {
		return "\uD83C\uDF96".repeat(level) + " " + name;
	}

	public int getLevel() {
		return level;
	}

	public String getName() {
		return name;
	}

}