package trainingStructure.trainingModule;

import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.personLinkModule.ResponderLink;
import linkStructure.trainingLinkModule.TrainingLink;
import responderStructure.responderModule.Responder;
import trainingStructure.trainingLinkModule.TrainingResponderLink;

import java.util.ArrayList;
import java.util.List;

public class Training {

	private final TrainingType trainingType;
	private final ExperienceLink experienceRequirement;
	private final List<TrainingLink> trainingRequirements;
	private final List<ResponderLink> responderLinks;

    public Training(TrainingType trainingType, ExperienceLink experienceRequirement, List<TrainingLink> trainingRequirements) {
        this.trainingType = trainingType;
		this.experienceRequirement = experienceRequirement;
		this.trainingRequirements = trainingRequirements;
		this.responderLinks = new ArrayList<>();
    }

	@Override
	public String toString() {
		return "[TRAINING] " + trainingType + " (requires: " + experienceRequirement + ", " + trainingRequirements + ")";
	}

	public TrainingType getTrainingType() {
		return trainingType;
	}

	public List<ResponderLink> getResponderLinks() {
		return responderLinks;
	}

	public void linkResponder(Responder responder) {
		if (responderLinks.stream()
				.noneMatch(item -> item.getResponder().equals(responder))) {
			TrainingResponderLink trainingResponderLink = new TrainingResponderLink(responder);
			responderLinks.add(trainingResponderLink);
		}
	}

}