package experienceStructure.experienceModule;

import java.util.ArrayList;
import java.util.List;

public class ExperienceManager {

    private List<Experience> experiences = new ArrayList<>();

    public ExperienceManager() {
        System.out.println("ExperienceManager initializing");
		initExperiences();
        System.out.println("ExperienceManager initialized successfully");
    }
	
	public Experience getExperience(int level) {
		// TODO
		return experiences.get(0);
	}

	public void addExperience(Experience Experience) {
		this.experiences.add(Experience);
	}
	
	public void initExperiences() {

		// TODO: loop for i from 1 to N
		int i = 1;
		addExperience(new Experience(i));
		
	}

}