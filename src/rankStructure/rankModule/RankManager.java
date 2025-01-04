package rankStructure.rankModule;

import departmentStructure.departmentModule.DepartmentType;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.rankLinkModule.RankLink;
import linkStructure.skillLinkModule.SkillLink;

import java.util.ArrayList;
import java.util.List;

public class RankManager {

	private List<Rank> ranks = new ArrayList<>();

	public RankManager() {
		System.out.println("RankManager initializing");
		initRanks();
		System.out.println("RankManager initialized successfully");
	}

	public Rank getRank(String name) {
		// TODO
		return ranks.get(0);
	}

	public void addRank(Rank Rank) {
		this.ranks.add(Rank);
	}

	public void initRanks() {

		// TODO: loop through DepartmentTypes
		List<RankLink> rankRequirements = new ArrayList<>();
		List<ExperienceLink> experienceRequirements = new ArrayList<>();
		List<SkillLink> skillRequirements = new ArrayList<>();
		Rank rank = new Rank(DepartmentType.FireDepartment, "Captain", rankRequirements, experienceRequirements,
				skillRequirements);
		addRank(rank);

	}

}