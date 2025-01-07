package rankStructure.rankModule;

import departmentStructure.departmentModule.Department;
import departmentStructure.departmentModule.DepartmentType;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.rankLinkModule.RankLink;
import linkStructure.skillLinkModule.SkillLink;
import rankStructure.rankLinkModule.RankRankLink;

import java.util.ArrayList;
import java.util.List;

public class RankManager {

	private final List<Rank> ranks = new ArrayList<>();

	public RankManager(Department department) {
		initRanks(department);
	}

	@Override
	public String toString() {
		return ranks.toString();
	}

	public Rank getRank(int level) {
		return ranks.stream()
				.filter(item -> item.getLevel() == level)
				.findFirst()
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

			String name = "";

			if (department.getDepartmentType().equals(DepartmentType.FIRE_DEPARTMENT)) {
				name = switch (level) {
					case 1 -> "Firefighter";
					case 2 -> "Engineer";
					case 3 -> "Lieutenant";
					case 4 -> "Captain";
					case 5 -> "Battalion Chief";
					case 6 -> "District Chief";
					case 7 -> "Commissioner";
					default -> "";
				};
			}

			else if (department.getDepartmentType().equals(DepartmentType.POLICE_DEPARTMENT)) {
				name = switch (level) {
					case 1 -> "Officer";
					case 2 -> "Sergeant";
					case 3 -> "Lieutenant";
					case 4 -> "Captain";
					case 5 -> "Commander";
					case 6 -> "Chief";
					case 7 -> "Superintendent";
					default -> "";
				};
			}

			else if (department.getDepartmentType().equals(DepartmentType.MEDIC_DEPARTMENT)) {
				name = switch (level) {
					case 1 -> "Emergency Medical Responder";
					case 2 -> "Emergency Medical Technician";
					case 3 -> "Paramedic";
					case 4 -> "Paramedic-in-Charge";
					case 5 -> "Paramedic Field Supervisor";
					case 6 -> "Paramedic Field Chief";
					case 7 -> "Chief Paramedic";
					default -> "";
				};
			}

			Rank rank = new Rank(level, name, rankRequirements, experienceRequirements, skillRequirements);
			addRank(rank);
		}

	}

}