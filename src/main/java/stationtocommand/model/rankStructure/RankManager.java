package stationtocommand.model.rankStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.experienceStructure.ExperienceLink;
import stationtocommand.model.rankTypeStructure.FireRankType;
import stationtocommand.model.rankTypeStructure.MedicRankType;
import stationtocommand.model.rankTypeStructure.PoliceRankType;
import stationtocommand.model.rankTypeStructure.RankType;
import stationtocommand.model.skillStructure.SkillLink;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RankManager {

	private final List<Rank> ranks = new ArrayList<>();

	public RankManager(Department department) {
		initRanks(department);
	}

	@Override
	public String toString() {
		return ranks.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public Rank getRank(int level) {
		return ranks.stream()
				.filter(item -> item.getRankType().getLevel() == level)
				.findAny()
				.orElse(null);
	}

	public void addRank(Rank Rank) {
		this.ranks.add(Rank);
	}

	public void initRanks(Department department) {

		for (int level = 1; level <= 7; level++) {

			List<RankLink> rankRequirements = new ArrayList<>();
			if (level > 1) {
				rankRequirements.add(new RankRankLink(getRank(level - 1)));
			}

			List<ExperienceLink> experienceRequirements = new ArrayList<>();
			// TODO: define Experience requirements for each Rank
			List<SkillLink> skillRequirements = new ArrayList<>();
			// TODO: define Skill requirements for each Rank

			RankType rankType = null;

			if (department.getDepartmentType().equals(DepartmentType.FIRE_DEPARTMENT)) {
				switch (level) {
					case 1 -> rankType = FireRankType.CANDIDATE_FIREFIGHTER;
					case 2 -> rankType = FireRankType.FIREFIGHTER;
					case 3 -> rankType = FireRankType.LIEUTENANT;
					case 4 -> rankType = FireRankType.CAPTAIN;
					case 5 -> rankType = FireRankType.BATTALION_CHIEF;
					case 6 -> rankType = FireRankType.DISTRICT_CHIEF;
					case 7 -> rankType = FireRankType.COMMISSIONER;
				}
			}

			else if (department.getDepartmentType().equals(DepartmentType.POLICE_DEPARTMENT)) {
				switch (level) {
					case 1 -> rankType = PoliceRankType.OFFICER;
					case 2 -> rankType = PoliceRankType.SERGEANT;
					case 3 -> rankType = PoliceRankType.LIEUTENANT;
					case 4 -> rankType = PoliceRankType.CAPTAIN;
					case 5 -> rankType = PoliceRankType.COMMANDER;
					case 6 -> rankType = PoliceRankType.CHIEF;
					case 7 -> rankType = PoliceRankType.SUPERINTENDENT;
				}
			}

			else if (department.getDepartmentType().equals(DepartmentType.MEDIC_DEPARTMENT)) {
				switch (level) {
					case 1 -> rankType = MedicRankType.EMR;
					case 2 -> rankType = MedicRankType.EMT;
					case 3 -> rankType = MedicRankType.PARAMEDIC;
					case 4 -> rankType = MedicRankType.PARAMEDIC_IN_CHARGE;
					case 5 -> rankType = MedicRankType.PARAMEDIC_SUPERVISOR;
					case 6 -> rankType = MedicRankType.PARAMEDIC_FIELD_CHIEF;
					case 7 -> rankType = MedicRankType.CHIEF_PARAMEDIC;
				}
			}

			Rank rank = new Rank(rankType, rankRequirements, experienceRequirements, skillRequirements);
			addRank(rank);
		}

	}

}