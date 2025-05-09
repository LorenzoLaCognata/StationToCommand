package stationtocommand.model.responderStructure;

import stationtocommand.model.departmentStructure.Department;
import stationtocommand.model.departmentStructure.DepartmentManager;
import stationtocommand.model.departmentStructure.DepartmentType;
import stationtocommand.model.experienceStructure.Experience;
import stationtocommand.model.experienceStructure.ExperienceLink;
import stationtocommand.model.locationStructure.Location;
import stationtocommand.model.personStructure.Gender;
import stationtocommand.model.rankStructure.Rank;
import stationtocommand.model.rankStructure.RankLink;
import stationtocommand.model.skillStructure.SkillLink;
import stationtocommand.model.stationStructure.Station;
import stationtocommand.model.unitStructure.Unit;
import stationtocommand.model.unitTypeStructure.FireUnitType;
import stationtocommand.model.unitTypeStructure.MedicUnitType;
import stationtocommand.model.unitTypeStructure.PoliceUnitType;
import stationtocommand.model.unitTypeStructure.UnitType;
import stationtocommand.model.utilsStructure.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResponderManager {

	private int responderIdCounter = 0;
	private final List<Responder> responders = new ArrayList<>();

	public ResponderManager(DepartmentManager departmentManager) {
		initResponders(departmentManager);
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
				.toList();
	}

	public Responder getPlayer() {
		return responders.stream()
				.filter(Responder::isPlayer)
				.findAny()
				.orElse(null);
	}

	public List<Responder> getResponders(Unit unit) {
		return responders.stream()
				.filter(item -> item.getUnitLink().getUnit().equals(unit))
				.toList();
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

					float responderRatio = responderCountByUnitType(department.getDepartmentType(), unit.getUnitType());
					int responderCount = Utils.randomIntegerFromRatio(responderRatio);
					for (int i=0; i<responderCount; i++) {
						// TODO: distribute Rank not randomly
						int level = Utils.randomGenerator.nextInt(1, 5);
						Rank rank = department.getRankManager().getRank(level);
						// TODO: distribute experience according to ranks
						Experience experience = new Experience(1);
						Location location = station.getLocation();
						Responder responder = new Responder(location, nextResponderId(), experience, rank, unit);
						addResponder(responder);
					}
				}
			}
			Rank rank = department.getRankManager().getRank(7);
			// TODO: distribute experience according to ranks
			Experience experience = new Experience(1);
			Location location = department.getStations().getFirst().getLocation();
			Responder responder = new Responder(location, nextResponderId(), experience, rank, department.getStations().getFirst().getUnits().getFirst());
			addResponder(responder);
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

	private static float responderCountByUnitType(DepartmentType departmentType, UnitType unitType) {
		float randomLimit = 0.0f;

		switch (departmentType) {
			case FIRE_DEPARTMENT -> {
				switch (unitType) {
					case FireUnitType.FIRE_ENGINE, FireUnitType.FIRE_TRUCK -> randomLimit = 4.0f;
					case FireUnitType.RESCUE_SQUAD -> randomLimit = 3.0f;
					default -> randomLimit = 0.0f;
				}
			}
			case POLICE_DEPARTMENT -> {
				switch (unitType) {
					case PoliceUnitType.PATROL_UNIT, PoliceUnitType.DETECTIVE_UNIT -> randomLimit = 2.0f;
					case PoliceUnitType.HOMICIDE_UNIT, PoliceUnitType.NARCOTICS_UNIT, PoliceUnitType.VICE_UNIT -> randomLimit = 3.0f;
					default -> randomLimit = 0.0f;
				}
			}
			case MEDIC_DEPARTMENT -> {
				switch (unitType) {
					case MedicUnitType.PRIMARY_CARE_UNIT -> randomLimit = 2.0f;
					case MedicUnitType.CRITICAL_CARE_UNIT -> randomLimit = 3.0f;
					default -> randomLimit = 0.0f;
				}
			}
		}
		return randomLimit;
	}

}