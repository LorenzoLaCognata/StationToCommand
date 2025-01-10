package trainingStructure.trainingModule;

import unitStructure.unitTypeModule.FireUnitType;
import linkStructure.experienceLinkModule.ExperienceLink;
import linkStructure.trainingLinkModule.TrainingLink;

import java.util.ArrayList;
import java.util.List;

public class TrainingManager {

    private final List<Training> trainings = new ArrayList<>();

    public TrainingManager() {
	    System.out.println("TrainingManager initializing");
			initTrainings();
      System.out.println("TrainingManager initialized successfully");
    }
	
	public Training getTraining(String name) {
		// TODO
		return trainings.get(0);
	}

	public void addTraining(Training Training) {
		this.trainings.add(Training);
	}
	
	public void initTrainings() {

		// TODO: loop through UnitTypes
		List<ExperienceLink> experienceRequirements = new ArrayList<>();
		List<TrainingLink> trainingRequirements = new ArrayList<>();
		Training training = new Training(FireUnitType.FIRE_ENGINE, "First Aid Basics", experienceRequirements, trainingRequirements);
		addTraining(training);
		
	}

}