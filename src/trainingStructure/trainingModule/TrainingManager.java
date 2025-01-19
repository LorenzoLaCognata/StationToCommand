package trainingStructure.trainingModule;

import experienceStructure.experienceModule.ExperienceManager;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.trainingLinkModule.TrainingLink;
import trainingStructure.trainingRequirementModule.TrainingExperienceRequirement;
import trainingStructure.trainingRequirementModule.TrainingTrainingRequirement;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TrainingManager {

    private final List<Training> trainings = new ArrayList<>();

    public TrainingManager(ExperienceManager experienceManager) {
		initTrainings(experienceManager);
    }

	@Override
	public String toString() {
		return trainings.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public Training getTraining(TrainingType trainingType) {
		return trainings.stream()
				.filter(item -> item.getTrainingType().equals(trainingType))
				.findAny()
				.orElse(null);
	}

	public void addTraining(Training Training) {
		this.trainings.add(Training);
	}
	
	public void initTrainings(ExperienceManager experienceManager) {

		ExperienceLink experienceRequirement = new TrainingExperienceRequirement(experienceManager.getExperience(1));
		List<TrainingLink> trainingRequirements = new ArrayList<>();
		addTraining(new Training(TrainingType.FIRST_AID, experienceRequirement, trainingRequirements));

		experienceRequirement = new TrainingExperienceRequirement(experienceManager.getExperience(2));
		trainingRequirements = List.of(new TrainingTrainingRequirement(getTraining(TrainingType.FIRST_AID)));
		addTraining(new Training(TrainingType.CPR, experienceRequirement, trainingRequirements));

		experienceRequirement = new TrainingExperienceRequirement(experienceManager.getExperience(1));
		trainingRequirements = new ArrayList<>();
		addTraining(new Training(TrainingType.FIRE_SAFETY, experienceRequirement, trainingRequirements));

		experienceRequirement = new TrainingExperienceRequirement(experienceManager.getExperience(2));
		trainingRequirements = List.of(new TrainingTrainingRequirement(getTraining(TrainingType.FIRE_SAFETY)));
		addTraining(new Training(TrainingType.FIRE_SCIENCE, experienceRequirement, trainingRequirements));

		experienceRequirement = new TrainingExperienceRequirement(experienceManager.getExperience(3));
		trainingRequirements = List.of(
									new TrainingTrainingRequirement(getTraining(TrainingType.FIRST_AID)),
									new TrainingTrainingRequirement(getTraining(TrainingType.FIRE_SAFETY))
								);
		addTraining(new Training(TrainingType.TRAUMA_CARE, experienceRequirement, trainingRequirements));

	}

}