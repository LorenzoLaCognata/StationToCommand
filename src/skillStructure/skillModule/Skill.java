package skillStructure.skillModule;

import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.personLinkModule.ResponderLink;
import linkStructure.skillLinkModule.SkillLink;
import linkStructure.trainingLinkModule.TrainingLink;
import responderStructure.responderModule.Responder;
import skillStructure.skillLinkModule.SkillResponderLink;

import java.util.ArrayList;
import java.util.List;

public class Skill {

	private final SkillType skillType;
	private final ExperienceLink experienceRequirement;
	private final List<SkillLink> skillRequirements;
	private final List<TrainingLink> trainingRequirements;
	// TODO: move Responder-Skill links in Responder and not in Skill
	private final List<ResponderLink> responderLinks;

	public Skill(SkillType skillType, ExperienceLink experienceRequirement,
			List<SkillLink> skillRequirements, List<TrainingLink> trainingRequirements) {
		this.skillType = skillType;
		this.experienceRequirement = experienceRequirement;
		this.skillRequirements = skillRequirements;
		this.trainingRequirements = trainingRequirements;
		this.responderLinks = new ArrayList<>();
	}

	@Override
	public String toString() {
		return skillType.toString();
	}

	public SkillType getSkillType() {
		return skillType;
	}

	public List<ResponderLink> getResponderLinks() {
		return responderLinks;
	}

	public void linkResponder(Responder responder) {
		if (responderLinks.stream()
				.noneMatch(item -> item.getResponder().equals(responder))) {
			SkillResponderLink skillResponderLink = new SkillResponderLink(responder);
			responderLinks.add(skillResponderLink);
		}
	}

}