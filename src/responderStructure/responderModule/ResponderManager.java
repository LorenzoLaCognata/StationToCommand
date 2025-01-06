package responderStructure.responderModule;

import departmentStructure.departmentModule.DepartmentManager;
import departmentStructure.departmentModule.DepartmentType;
import experienceStructure.experienceModule.Experience;
import experienceStructure.experienceModule.ExperienceManager;
import locationStructure.locationModule.Location;
import rankStructure.rankModule.Rank;
import responderStructure.responderLinkModule.ResponderUnitLink;
import skillStructure.skillModule.SkillManager;
import unitStructure.unitModule.FireUnit;
import unitStructure.unitTypeModule.FireUnitType;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.organizationLinkModule.UnitLink;
import linkStructure.rankLinkModule.RankLink;
import linkStructure.skillLinkModule.SkillLink;

import java.util.ArrayList;
import java.util.List;

public class ResponderManager {

	private List<Responder> responders = new ArrayList<>();
	private ExperienceManager experienceManager;
	private SkillManager skillManager;
	// TODO
	// private ActionManager actionManager;

	public ResponderManager(DepartmentManager departmentManager) {
		System.out.println("ResponderManager initializing");
		initResponders(departmentManager);
		this.experienceManager = new ExperienceManager();
		this.skillManager = new SkillManager();
		// TODO
		// this.actionManager = new ActionManager();
		System.out.println("ResponderManager initialized successfully");
	}

	public Responder getResponder(String name) {
		// TODO
		return responders.get(0);
	}

	public void addResponder(Responder responder) {
		this.responders.add(responder);
	}

	public void initResponders(DepartmentManager departmentManager) {

		// TODO: loop through Stations and Units
		FireUnit unit = new FireUnit(FireUnitType.FIRE_ENGINE, 0);
		Experience experience = new Experience(1);

		List<RankLink> rankRequirements = new ArrayList<>();
		List<ExperienceLink> experienceRequirements = new ArrayList<>();
		List<SkillLink> skillRequirements = new ArrayList<>();
		Rank rank = new Rank(DepartmentType.FIRE_DEPARTMENT, "Captain", rankRequirements, experienceRequirements,
				skillRequirements);
		Location location = new Location(0.0f, 0.0f); // TODO: random coordinates
		List<UnitLink> unitLinks = new ArrayList<>() {
		};
		unitLinks.add(new ResponderUnitLink(unit));
		Responder responder = new Responder("John Doe", location, experience, rank, unitLinks);
		addResponder(responder);

	}

}