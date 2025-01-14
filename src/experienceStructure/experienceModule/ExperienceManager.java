package experienceStructure.experienceModule;

import java.util.ArrayList;
import java.util.List;

public class ExperienceManager {

    private final List<Experience> experiences = new ArrayList<>();

    public ExperienceManager() {
		initExperiences();
    }

	@Override
	public String toString() {
		return experiences.toString();
	}

	public Experience getExperience(int level) {
		return experiences.stream()
				.filter(item -> item.getLevel() == level)
				.findFirst()
				.orElse(null);
	}

	public void addExperience(Experience Experience) {
		this.experiences.add(Experience);
	}
	
	public void initExperiences() {

		for (int i=1; i<=20; i++) {
			addExperience(new Experience(i));
		}

	}

}