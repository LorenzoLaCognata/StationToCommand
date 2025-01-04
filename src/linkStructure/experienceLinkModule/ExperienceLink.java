package linkStructure.experienceLinkModule;

import experienceStructure.experienceModule.Experience;

public abstract class ExperienceLink {

    private Experience experience;

    public ExperienceLink(Experience experience) {
        System.out.println("ExperienceLink initializing");
        this.experience = experience;
		System.out.println("ExperienceLink initialized successfully");
    }
	
}