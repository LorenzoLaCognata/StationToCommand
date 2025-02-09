package model.experienceStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExperienceManager {

    private final List<Experience> experiences = new ArrayList<>();

    public ExperienceManager() {
		initExperiences();
    }

	@Override
	public String toString() {
		return experiences.stream()
				.map(item -> "\t- " + item)
				.collect(Collectors.joining("\n"));
	}

	public Experience getExperience(int level) {
		return experiences.stream()
				.filter(item -> item.level() == level)
				.findAny().
				orElse(null);
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