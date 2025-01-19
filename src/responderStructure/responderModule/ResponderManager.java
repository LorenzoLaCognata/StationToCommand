package responderStructure.responderModule;

import actionStructure.actionModule.ActionManager;
import departmentStructure.departmentModule.Department;
import departmentStructure.departmentModule.DepartmentManager;
import departmentStructure.departmentModule.DepartmentType;
import experienceStructure.experienceModule.Experience;
import experienceStructure.experienceModule.ExperienceManager;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.rankLinkModule.RankLink;
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
import java.util.stream.Collectors;

public class ResponderManager {

	private int responderIdCounter = 0;
	private final List<Responder> responders = new ArrayList<>();
	private final ExperienceManager experienceManager;
	private final SkillManager skillManager;
	private final ActionManager actionManager;

	public ResponderManager(DepartmentManager departmentManager) {
		initResponders(departmentManager);
		this.experienceManager = new ExperienceManager();
		this.skillManager = new SkillManager();
		this.actionManager = new ActionManager();
	}

	@Override
	public String toString() {
		return responders.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public List<Responder> getResponders() {
		return responders;
	}

	public List<Responder> getResponders(String firstName, String lastName) {
		return responders.stream()
				.filter(item -> item.getFirstName().equals(firstName) && item.getLastName().equals(lastName))
				.collect(Collectors.toList());
	}

	public List<Responder> getResponders(Unit unit) {
		return responders.stream()
				.filter(item -> item.getUnitLink().getUnit().equals(unit))
				.collect(Collectors.toList());
	}

	public void addResponder(Responder responder) {
		this.responders.add(responder);
	}

	public int nextResponderId() {
		this.responderIdCounter++;
		return responderIdCounter;
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
							Responder responder = new Responder(location, nextResponderId(), experience, rank, unit);
							addResponder(responder);
						}
					}
				}
			}
		}

		Department playerDepartment = departmentManager.getDepartment(DepartmentType.FIRE_DEPARTMENT);
		Station playerStation = playerDepartment.getStationManager().getStation(1);
		Unit playerUnit = playerStation.getUnitManager().getUnits(FireUnitType.FIRE_ENGINE).getFirst();
		Rank playerRank = playerDepartment.getRankManager().getRank(1);
		Experience playerExperience = new Experience(1);
		Location playerLocation = playerStation.getLocation();

		Responder player = new Responder("Giulia", "Carlà", Gender.FEMALE, playerLocation,  nextResponderId(), true, playerExperience, playerRank, playerUnit);

		Responder relatedResponder = getResponders(playerUnit).getFirst();
		player.linkResponder(relatedResponder, 0.2f);

		addResponder(player);

	}

}