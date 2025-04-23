package stationtocommand.model.rankStructure;

import stationtocommand.model.experienceStructure.ExperienceLink;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.skillStructure.SkillLink;

import java.util.List;

public class Rank {

	private final RankType rankType;
	private final List<RankLink> rankRequirements;
	private final List<ExperienceLink> experienceRequirements;
	private final List<SkillLink> skillRequirements;

	public Rank(RankType rankType, List<RankLink> rankRequirements, List<ExperienceLink>
		experienceRequirements, List<SkillLink> skillRequirements) {
		this.rankType = rankType;
		this.rankRequirements = rankRequirements;
		this.experienceRequirements = experienceRequirements;
		this.skillRequirements = skillRequirements;
	}

	@Override
	public String toString() {
		return rankType.toString();
	}

	public RankType getRankType() {
		return rankType;
	}

}