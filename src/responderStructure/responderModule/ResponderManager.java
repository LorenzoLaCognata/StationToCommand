package responderStructure.responderModule;

import actionStructure.actionModule.ActionManager;
import departmentStructure.departmentModule.Department;
import departmentStructure.departmentModule.DepartmentManager;
import departmentStructure.departmentModule.DepartmentType;
import experienceStructure.experienceModule.Experience;
import experienceStructure.experienceModule.ExperienceManager;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.rankLinkModule.RankLink;
import linkStructure.responderLinkModule.ResponderLink;
import linkStructure.skillLinkModule.SkillLink;
import locationStructure.locationModule.Location;
import personStructure.personModule.Gender;
import rankStructure.rankModule.Rank;
import skillStructure.skillModule.SkillManager;
import stationStructure.stationModule.Station;
import unitStructure.unitModule.Unit;
import unitStructure.unitTypeModule.FireUnitType;
import utilsStructure.utilsModule.Utils;

import java.util.ArrayList;
import java.util.List;

public class ResponderManager {

	private final List<Responder> responders = new ArrayList<>();
	private final ExperienceManager experienceManager;
	private final SkillManager skillManager;
	private final ActionManager actionManager;

	public ResponderManager(DepartmentManager departmentManager) {
		System.out.println("ResponderManager initializing");
		initResponders(departmentManager);
		this.experienceManager = new ExperienceManager();
		this.skillManager = new SkillManager();
		this.actionManager = new ActionManager();
		System.out.println("ResponderManager initialized successfully");
	}

	@Override
	public String toString() {
		return responders.toString();
	}

	public Responder getResponder(String firstName, String lastName) {
		return responders.stream()
				.filter(item -> item.getFirstName().equals(firstName) && item.getLastName().equals(lastName))
				.findFirst()
				.orElse(null);
	}

	public void addResponder(Responder responder) {
		this.responders.add(responder);
	}

	public void initResponders(DepartmentManager departmentManager) {

		// TODO: initialize ranks in RankManager and use those without recreating it each time
		List<RankLink> rankRequirements = new ArrayList<>();
		List<ExperienceLink> experienceRequirements = new ArrayList<>();
		List<SkillLink> skillRequirements = new ArrayList<>();

		for (Department department: departmentManager.getDepartments()) {
			for (Station station : department.getStations()) {
				for (Unit unit : station.getUnits()) {
					for (int level = 4; level >= 1; level--) {

						float randomLimit = switch (level) {
							case 1 -> 1.0f;
							case 2 -> 0.6f;
							case 3 -> 0.9f;
							case 4 -> 0.3f;
							case 5 -> 0.0f;
							case 6 -> 0.0f;
							case 7 -> 0.0f;
							default -> 0.0f;
						};
						if (Utils.randomGenerator.nextFloat() < randomLimit) {
							Rank rank = department.getRankManager().getRank(level);
							// TODO: distribute experience according to ranks
							Experience experience = new Experience(1);
							Location location = station.getLocation();
							List<ResponderLink> responderLinks = new ArrayList<>();
							Responder responder = new Responder(location, experience, rank, unit, responderLinks);
							addResponder(responder);
						}
					}
				}
			}
		}

		Department playerDepartment = departmentManager.getDepartment(DepartmentType.FIRE_DEPARTMENT);
		Station playerStation = playerDepartment.getStationManager().getStation(1);
		Unit playerUnit = playerStation.getUnitManager().getUnit(FireUnitType.FIRE_ENGINE);
		Rank playerRank = playerDepartment.getRankManager().getRank(1);
		Experience playerExperience = new Experience(1);
		Location playerLocation = playerStation.getLocation();
		List<ResponderLink> playerResponderLinks = new ArrayList<>();
		Responder player = new Responder("Giulia", "Carlà", Gender.FEMALE, playerLocation, playerExperience, playerRank, playerUnit, playerResponderLinks);
		player.setPlayer(true);
		addResponder(player);

		for (Department department : departmentManager.getDepartments()) {
			System.out.println("\n" + department);
			for (Station station : department.getStations()) {
				System.out.println("\n\t\t- " + station);
				for (Unit unit : station.getUnits()) {
					System.out.println("\t\t\t\t- " + unit);
					for (Responder responder : responders) {
						if (responder.getUnitLink().getUnit().equals(unit)) {
							System.out.println("\t\t\t\t\t\t- " + responder);
						}
					}
				}
			}
		}

	}

}